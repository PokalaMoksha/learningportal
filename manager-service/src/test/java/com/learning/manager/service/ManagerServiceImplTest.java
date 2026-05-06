package com.learning.manager.service;

import com.learning.manager.client.*;
import com.learning.manager.repository.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerServiceImplTest {

    @Mock
    private ManagerRepository
            managerRepository;

    @Mock
    private AuthClient authClient;

    @Mock
    private EmployeeClient employeeClient;

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
    private ManagerServiceImpl managerService;

    private Manager manager;
    private UserResponse userResponse;
    private ManagerResponse managerResponse;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {

        manager = Manager.builder()
                .id(1L)
                .managerName("abdul")
                .managerCode("MGR001")
                .userId(1L)
                .csmId(1L)
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .username("abdul")
                .email("abdul@learning.com")
                .role(Role.MANAGER)
                .isActive(true)
                .build();

        managerResponse =
            ManagerResponse.builder()
                .id(1L)
                .managerName("abdul")
                .managerCode("MGR001")
                .userId(1L)
                .csmId(1L)
                .username("abdul")
                .email("abdul@learning.com")
                .role("MANAGER")
                .isActive(true)
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

    // ── getMyTeam Tests ───────────────────────

    @Test
    void getMyTeam_Success() {

        when(authClient
            .getUserByUsername("abdul"))
            .thenReturn(userResponse);

        when(managerRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(manager));

        when(employeeClient
            .getEmployeesByManager(1L))
            .thenReturn(
                Arrays.asList(employeeResponse));

        List<EmployeeResponse> result =
            managerService.getMyTeam("abdul");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("moksha",
            result.get(0).getEmployeeName());

        verify(authClient)
            .getUserByUsername("abdul");
        verify(managerRepository)
            .findByUserId(1L);
        verify(employeeClient)
            .getEmployeesByManager(1L);
    }

    @Test
    void getMyTeam_ManagerNotFound() {

        when(authClient
            .getUserByUsername("unknown"))
            .thenReturn(userResponse);

        when(managerRepository
            .findByUserId(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> managerService
                      .getMyTeam("unknown"));
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
            managerService
                .getEmployeeLearning(1L, "abdul");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(
            LearningTrack.BACKEND,
            result.get(0).getLearningTrack());
    }

    @Test
    void getEmployeeLearning_Empty() {

        when(learningClient
            .getLearningByEmployee(99L))
            .thenReturn(List.of());

        List<LearningResponse> result =
            managerService
                .getEmployeeLearning(
                 99L, "abdul");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ── getEmployeeCertificates Tests ─────────

    @Test
    void getEmployeeCertificates_Success() {

        CertificateResponse cert =
            CertificateResponse.builder()
                .id(1L)
                .employeeId(1L)
                .platform("AWS")
                .certificateName(
                    "AWS Associate")
                .build();

        when(certificateClient
            .getCertificatesByEmployee(1L))
            .thenReturn(Arrays.asList(cert));

        List<CertificateResponse> result =
            managerService
                .getEmployeeCertificates(
                 1L, "abdul");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("AWS",
            result.get(0).getPlatform());
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
                .build();

        when(authClient
            .getUserByUsername("abdul"))
            .thenReturn(userResponse);

        when(managerRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(manager));

        when(employeeClient
            .addEmployee(
             any(UserRequest.class)))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            managerService
                .addEmployee(request, "abdul");

        assertNotNull(result);
        verify(employeeClient)
            .addEmployee(any(UserRequest.class));
    }

    // ── updateEmployee Tests ──────────────────

    @Test
    void updateEmployee_Success() {

        UserRequest request =
            UserRequest.builder()
                .name("Updated Name")
                .build();

        when(employeeClient
            .updateEmployee(1L, request))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            managerService
                .updateEmployee(
                 1L, request, "abdul");

        assertNotNull(result);
        verify(employeeClient)
            .updateEmployee(1L, request);
    }

    // ── getAllManagers Tests ───────────────────

    @Test
    void getAllManagers_Success() {

        when(managerRepository.findAll())
            .thenReturn(Arrays.asList(manager));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            manager,
            ManagerResponse.class))
            .thenReturn(managerResponse);

        List<ManagerResponse> result =
            managerService.getAllManagers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("abdul",
            result.get(0).getManagerName());
    }

    // ── getManagerById Tests ──────────────────

    @Test
    void getManagerById_Success() {

        when(managerRepository.findById(1L))
            .thenReturn(Optional.of(manager));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            manager,
            ManagerResponse.class))
            .thenReturn(managerResponse);

        ManagerResponse result =
            managerService.getManagerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("abdul",
            result.getManagerName());
    }

    @Test
    void getManagerById_NotFound() {

        when(managerRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> managerService
                      .getManagerById(99L));
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
                .csmId(1L)
                .build();

        when(authClient.createUser(request))
            .thenReturn(userResponse);

        when(managerRepository
            .save(any(Manager.class)))
            .thenReturn(manager);

        when(modelMapper.map(
            any(Manager.class),
            eq(ManagerResponse.class)))
            .thenReturn(managerResponse);

        ManagerResponse result =
            managerService.addManager(request);

        assertNotNull(result);
        verify(authClient).createUser(request);
        verify(managerRepository)
            .save(any(Manager.class));
    }

    // ── updateManager Tests ───────────────────

    @Test
    void updateManager_Success() {

        UserRequest request =
            UserRequest.builder()
                .name("Abdul Updated")
                .build();

        when(managerRepository.findById(1L))
            .thenReturn(Optional.of(manager));

        when(managerRepository
            .save(any(Manager.class)))
            .thenReturn(manager);

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            any(Manager.class),
            eq(ManagerResponse.class)))
            .thenReturn(managerResponse);

        ManagerResponse result =
            managerService
                .updateManager(1L, request);

        assertNotNull(result);
        verify(managerRepository)
            .save(any(Manager.class));
    }

    @Test
    void updateManager_NotFound() {

        UserRequest request =
            UserRequest.builder()
                .name("Updated")
                .build();

        when(managerRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> managerService
                      .updateManager(
                       99L, request));
    }

    // ── existsByIdAndCsmId Tests ──────────────

    @Test
    void existsByIdAndCsmId_True() {

        when(managerRepository
            .existsByIdAndCsmId(1L, 1L))
            .thenReturn(true);

        boolean result =
            managerService
                .existsByIdAndCsmId(1L, 1L);

        assertTrue(result);
    }

    @Test
    void existsByIdAndCsmId_False() {

        when(managerRepository
            .existsByIdAndCsmId(1L, 99L))
            .thenReturn(false);

        boolean result =
            managerService
                .existsByIdAndCsmId(1L, 99L);

        assertFalse(result);
    }

    // ── getManagersByCsm Tests ────────────────

    @Test
    void getManagersByCsm_Success() {

        when(managerRepository
            .findByCsmId(1L))
            .thenReturn(Arrays.asList(manager));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            manager,
            ManagerResponse.class))
            .thenReturn(managerResponse);

        List<ManagerResponse> result =
            managerService.getManagersByCsm(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getManagersByCsm_Empty() {

        when(managerRepository
            .findByCsmId(99L))
            .thenReturn(List.of());

        List<ManagerResponse> result =
            managerService.getManagersByCsm(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
