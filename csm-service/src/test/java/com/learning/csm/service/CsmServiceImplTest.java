package com.learning.csm.service;

import com.learning.csm.client.*;
import com.learning.csm.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.enums.Role;
import com.learning.common.enums.LearningTrack;
import com.learning.common.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsmServiceImplTest {

    @Mock
    private CsmRepository csmRepository;

    @Mock
    private TeamHierarchyRepository
            teamHierarchyRepository;

    @Mock
    private AuthClient authClient;

    @Mock
    private EmployeeClient employeeClient;

    @Mock
    private ManagerClient managerClient;

    @Mock
    private LearningClient learningClient;

    @Mock
    private CertificateClient
            certificateClient;

    @Mock
    private ReportClient reportClient;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CsmServiceImpl csmService;

    private Csm csm;
    private UserResponse userResponse;
    private CsmResponse csmResponse;
    private ManagerResponse managerResponse;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {

        csm = Csm.builder()
                .id(1L)
                .csmName("santhosh")
                .csmCode("CSM001")
                .userId(1L)
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .username("santhosh")
                .email("santhosh@learning.com")
                .role(Role.CSM)
                .isActive(true)
                .build();

        csmResponse = CsmResponse.builder()
                .id(1L)
                .csmName("santhosh")
                .csmCode("CSM001")
                .userId(1L)
                .username("santhosh")
                .email("santhosh@learning.com")
                .role("CSM")
                .isActive(true)
                .build();

        managerResponse =
            ManagerResponse.builder()
                .id(1L)
                .managerName("abdul")
                .managerCode("MGR001")
                .csmId(1L)
                .username("abdul")
                .email("abdul@learning.com")
                .build();

        employeeResponse =
            EmployeeResponse.builder()
                .id(1L)
                .employeeName("moksha")
                .employeeCode("EMP001")
                .managerId(1L)
                .username("moksha")
                .email("moksha@learning.com")
                .build();
    }

    // ── getMyManagers Tests ───────────────────

    @Test
    void getMyManagers_Success() {

        when(authClient
            .getUserByUsername("santhosh"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(csm));

        when(managerClient
            .getManagersByCsm(1L))
            .thenReturn(
                Arrays.asList(managerResponse));

        List<ManagerResponse> result =
            csmService.getMyManagers("santhosh");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("abdul",
            result.get(0).getManagerName());

        verify(authClient)
            .getUserByUsername("santhosh");
        verify(csmRepository)
            .findByUserId(1L);
        verify(managerClient)
            .getManagersByCsm(1L);
    }

    @Test
    void getMyManagers_CsmNotFound() {

        when(authClient
            .getUserByUsername("unknown"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            CsmNotFoundException.class,
            () -> csmService
                      .getMyManagers("unknown"));
    }

    // ── getMyTeams Tests ──────────────────────

    @Test
    void getMyTeams_Success() {

        when(authClient
            .getUserByUsername("santhosh"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(csm));

        when(managerClient
            .getManagersByCsm(1L))
            .thenReturn(
                Arrays.asList(managerResponse));

        when(employeeClient
            .getEmployeesByManager(1L))
            .thenReturn(
                Arrays.asList(employeeResponse));

        List<EmployeeResponse> result =
            csmService.getMyTeams("santhosh");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("moksha",
            result.get(0).getEmployeeName());
    }

    @Test
    void getMyTeams_NoManagers() {

        when(authClient
            .getUserByUsername("santhosh"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(csm));

        when(managerClient
            .getManagersByCsm(1L))
            .thenReturn(List.of());

        List<EmployeeResponse> result =
            csmService.getMyTeams("santhosh");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ── getCsmById Tests ──────────────────────

    @Test
    void getCsmById_Success() {

        when(csmRepository.findById(1L))
            .thenReturn(Optional.of(csm));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            csm, CsmResponse.class))
            .thenReturn(csmResponse);

        CsmResponse result =
            csmService.getCsmById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("santhosh",
            result.getCsmName());
    }

    @Test
    void getCsmById_NotFound() {

        when(csmRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> csmService.getCsmById(99L));
    }

    // ── addCsm Tests ──────────────────────────

    @Test
    void addCsm_Success() {

        UserRequest request =
            UserRequest.builder()
                .username("newcsm")
                .password("password123")
                .email("newcsm@learning.com")
                .role("CSM")
                .name("New CSM")
                .code("CSM100")
                .build();

        when(authClient.createUser(request))
            .thenReturn(userResponse);

        when(csmRepository
            .save(any(Csm.class)))
            .thenReturn(csm);

        when(modelMapper.map(
            any(Csm.class),
            eq(CsmResponse.class)))
            .thenReturn(csmResponse);

        CsmResponse result =
            csmService.addCsm(request);

        assertNotNull(result);
        verify(authClient).createUser(request);
        verify(csmRepository)
            .save(any(Csm.class));
    }

    // ── addManager Tests ──────────────────────

    @Test
    void addManager_Success() {

        UserRequest request =
            UserRequest.builder()
                .username("newmanager")
                .password("password123")
                .email("new@learning.com")
                .role("MANAGER")
                .name("New Manager")
                .code("MGR100")
                .build();

        when(authClient
            .getUserByUsername("santhosh"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(csm));

        when(managerClient
            .addManager(any(UserRequest.class)))
            .thenReturn(managerResponse);

        ManagerResponse result =
            csmService.addManager(
                request, "santhosh");

        assertNotNull(result);
        verify(managerClient)
            .addManager(any(UserRequest.class));
    }

    // ── addEmployee Tests ─────────────────────

    @Test
    void addEmployee_Success() {

        UserRequest request =
            UserRequest.builder()
                .username("newemployee")
                .password("password123")
                .email("new@learning.com")
                .role("EMPLOYEE")
                .name("New Employee")
                .code("EMP100")
                .managerId(1L)
                .build();

        TeamHierarchy hierarchy =
            TeamHierarchy.builder()
                .id(1L)
                .csmId(1L)
                .managerId(1L)
                .employeeId(1L)
                .isActive(true)
                .effectiveFrom(LocalDate.now())
                .build();

        when(authClient
            .getUserByUsername("santhosh"))
            .thenReturn(userResponse);

        when(csmRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(csm));

        when(employeeClient
            .addEmployee(any(UserRequest.class)))
            .thenReturn(employeeResponse);

        when(teamHierarchyRepository
            .save(any(TeamHierarchy.class)))
            .thenReturn(hierarchy);

        EmployeeResponse result =
            csmService.addEmployee(
                request, "santhosh");

        assertNotNull(result);
        verify(employeeClient)
            .addEmployee(any(UserRequest.class));
        verify(teamHierarchyRepository)
            .save(any(TeamHierarchy.class));
    }

    // ── getEmployeeLearning Tests ─────────────

    @Test
    void getEmployeeLearning_Success() {

        LearningResponse learning =
            LearningResponse.builder()
                .id(1L)
                .employeeId(1L)
                .learningTrack(
                    LearningTrack.BACKEND)
                .build();

        when(learningClient
            .getLearningByEmployee(1L))
            .thenReturn(Arrays.asList(learning));

        List<LearningResponse> result =
            csmService.getEmployeeLearning(
                1L, "santhosh");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(
            LearningTrack.BACKEND,
            result.get(0).getLearningTrack());
    }

    // ── getEmployeeCertificates Tests ─────────

    @Test
    void getEmployeeCertificates_Success() {

        CertificateResponse cert =
            CertificateResponse.builder()
                .id(1L)
                .employeeId(1L)
                .platform("GCP")
                .certificateName(
                    "GCP Associate")
                .build();

        when(certificateClient
            .getCertificatesByEmployee(1L))
            .thenReturn(Arrays.asList(cert));

        List<CertificateResponse> result =
            csmService.getEmployeeCertificates(
                1L, "santhosh");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("GCP",
            result.get(0).getPlatform());
    }

    // ── reassignEmployee Tests ────────────────

    @Test
    void reassignEmployee_Success() {

        ReassignEmployeeRequest request =
            new ReassignEmployeeRequest();
        request.setEmployeeId(1L);
        request.setNewManagerId(2L);

        TeamHierarchy hierarchy =
            TeamHierarchy.builder()
                .id(1L)
                .csmId(1L)
                .managerId(1L)
                .employeeId(1L)
                .isActive(true)
                .effectiveFrom(LocalDate.now())
                .build();

        when(employeeClient
            .updateEmployee(
             eq(1L),
             any(UserRequest.class)))
            .thenReturn(employeeResponse);

        when(teamHierarchyRepository
            .findByEmployeeId(1L))
            .thenReturn(Optional.of(hierarchy));

        when(teamHierarchyRepository
            .save(any(TeamHierarchy.class)))
            .thenReturn(hierarchy);

        assertDoesNotThrow(() ->
            csmService.reassignEmployee(
                request, "santhosh"));

        verify(teamHierarchyRepository)
            .save(any(TeamHierarchy.class));
    }

    @Test
    void reassignEmployee_HierarchyNotFound() {

        ReassignEmployeeRequest request =
            new ReassignEmployeeRequest();
        request.setEmployeeId(99L);
        request.setNewManagerId(2L);

        when(employeeClient
            .updateEmployee(
             eq(99L),
             any(UserRequest.class)))
            .thenReturn(employeeResponse);

        when(teamHierarchyRepository
            .findByEmployeeId(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> csmService.reassignEmployee(
                      request, "santhosh"));
    }

    // ── getAllCsms Tests ───────────────────────

    @Test
    void getAllCsms_Success() {

        when(csmRepository.findAll())
            .thenReturn(Arrays.asList(csm));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            csm, CsmResponse.class))
            .thenReturn(csmResponse);

        List<CsmResponse> result =
            csmService.getAllCsms();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("santhosh",
            result.get(0).getCsmName());
    }
}
