package com.learning.employee.service;

import com.learning.employee.client.*;
import com.learning.employee.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.enums.LearningTrack;
import com.learning.common.enums.LearningLevel;
import com.learning.common.enums.Role;
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
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository
            employeeRepository;

    @Mock
    private AuthClient authClient;

    @Mock
    private ManagerClient managerClient;

    @Mock
    private LearningClient learningClient;

    @Mock
    private CertificateClient
            certificateClient;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl
            employeeService;

    private Employee employee;
    private UserResponse userResponse;
    private EmployeeResponse employeeResponse;

    @BeforeEach
    void setUp() {

        employee = Employee.builder()
                .id(1L)
                .employeeName("moksha")
                .employeeCode("EMP001")
                .userId(1L)
                .managerId(1L)
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .username("moksha")
                .email("moksha@learning.com")
                .role(Role.EMPLOYEE)
                .isActive(true)
                .build();

        employeeResponse =
            EmployeeResponse.builder()
                .id(1L)
                .employeeName("moksha")
                .employeeCode("EMP001")
                .userId(1L)
                .managerId(1L)
                .username("moksha")
                .email("moksha@learning.com")
                .role("EMPLOYEE")
                .isActive(true)
                .build();
    }

    // ── getMyDetails Tests ────────────────────

    @Test
    void getMyDetails_Success() {

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(employee));

        when(modelMapper.map(
            employee,
            EmployeeResponse.class))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            employeeService
                .getMyDetails("moksha");

        assertNotNull(result);
        assertEquals("moksha",
            result.getUsername());
        assertEquals("moksha@learning.com",
            result.getEmail());
        assertEquals("EMPLOYEE",
            result.getRole());

        verify(authClient)
            .getUserByUsername("moksha");
        verify(employeeRepository)
            .findByUserId(1L);
    }

    @Test
    void getMyDetails_UserNotFound() {

        when(authClient
            .getUserByUsername("unknown"))
            .thenThrow(new
                UserNotFoundException(
                "User not found!"));

        assertThrows(
            UserNotFoundException.class,
            () -> employeeService
                      .getMyDetails("unknown"));
    }

    @Test
    void getMyDetails_EmployeeNotFound() {

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService
                      .getMyDetails("moksha"));
    }

    // ── getMyLearning Tests ───────────────────

    @Test
    void getMyLearning_Success() {

        LearningResponse learning =
            LearningResponse.builder()
                .id(1L)
                .employeeId(1L)
                .learningTrack(
                    LearningTrack.BACKEND)
                .build();

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(employee));

        when(learningClient
            .getLearningByEmployee(1L))
            .thenReturn(Arrays.asList(learning));

        List<LearningResponse> result =
            employeeService
                .getMyLearning("moksha");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(
            LearningTrack.BACKEND,
            result.get(0).getLearningTrack());
    }

    @Test
    void getMyLearning_EmptyList() {

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(employee));

        when(learningClient
            .getLearningByEmployee(1L))
            .thenReturn(List.of());

        List<LearningResponse> result =
            employeeService
                .getMyLearning("moksha");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ── getMyManager Tests ────────────────────

    @Test
    void getMyManager_Success() {

        ManagerResponse managerResponse =
            ManagerResponse.builder()
                .id(1L)
                .managerName("abdul")
                .username("abdul")
                .email("abdul@learning.com")
                .build();

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(employee));

        when(managerClient
            .getManagerById(1L))
            .thenReturn(managerResponse);

        ManagerResponse result =
            employeeService
                .getMyManager("moksha");

        assertNotNull(result);
        assertEquals("abdul",
            result.getManagerName());
    }

    // ── getMyCertificates Tests ───────────────

    @Test
    void getMyCertificates_Success() {

        CertificateResponse cert =
            CertificateResponse.builder()
                .id(1L)
                .employeeId(1L)
                .platform("AWS")
                .certificateName(
                    "AWS Associate")
                .build();

        when(authClient
            .getUserByUsername("moksha"))
            .thenReturn(userResponse);

        when(employeeRepository
            .findByUserId(1L))
            .thenReturn(Optional.of(employee));

        when(certificateClient
            .getCertificatesByEmployee(1L))
            .thenReturn(Arrays.asList(cert));

        List<CertificateResponse> result =
            employeeService
                .getMyCertificates("moksha");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("AWS",
            result.get(0).getPlatform());
    }

    // ── getAllEmployees Tests ──────────────────

    @Test
    void getAllEmployees_Success() {

        when(employeeRepository.findAll())
            .thenReturn(Arrays.asList(employee));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            employee,
            EmployeeResponse.class))
            .thenReturn(employeeResponse);

        List<EmployeeResponse> result =
            employeeService.getAllEmployees();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // ── getEmployeeById Tests ─────────────────

    @Test
    void getEmployeeById_Success() {

        when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(employee));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            employee,
            EmployeeResponse.class))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            employeeService
                .getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getEmployeeById_NotFound() {

        when(employeeRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService
                      .getEmployeeById(99L));
    }

    // ── getEmployeesByManager Tests ───────────

    @Test
    void getEmployeesByManager_Success() {

        when(employeeRepository
            .findByManagerId(1L))
            .thenReturn(Arrays.asList(employee));

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            employee,
            EmployeeResponse.class))
            .thenReturn(employeeResponse);

        List<EmployeeResponse> result =
            employeeService
                .getEmployeesByManager(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
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

        when(authClient.createUser(request))
            .thenReturn(userResponse);

        when(employeeRepository
            .save(any(Employee.class)))
            .thenReturn(employee);

        when(modelMapper.map(
            any(Employee.class),
            eq(EmployeeResponse.class)))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            employeeService
                .addEmployee(request);

        assertNotNull(result);
        verify(authClient).createUser(request);
        verify(employeeRepository)
            .save(any(Employee.class));
    }

    // ── updateEmployee Tests ──────────────────

    @Test
    void updateEmployee_Success() {

        UserRequest request =
            UserRequest.builder()
                .name("Moksha Updated")
                .build();

        when(employeeRepository.findById(1L))
            .thenReturn(Optional.of(employee));

        when(employeeRepository
            .save(any(Employee.class)))
            .thenReturn(employee);

        when(authClient.getUserById(1L))
            .thenReturn(userResponse);

        when(modelMapper.map(
            any(Employee.class),
            eq(EmployeeResponse.class)))
            .thenReturn(employeeResponse);

        EmployeeResponse result =
            employeeService
                .updateEmployee(1L, request);

        assertNotNull(result);
        verify(employeeRepository)
            .save(any(Employee.class));
    }

    @Test
    void updateEmployee_NotFound() {

        UserRequest request =
            UserRequest.builder()
                .name("Updated")
                .build();

        when(employeeRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService
                      .updateEmployee(
                       99L, request));
    }

    // ── existsByIdAndManagerId Tests ──────────

    @Test
    void existsByIdAndManagerId_True() {

        when(employeeRepository
            .existsByIdAndManagerId(1L, 1L))
            .thenReturn(true);

        boolean result =
            employeeService
                .existsByIdAndManagerId(1L, 1L);

        assertTrue(result);
    }

    @Test
    void existsByIdAndManagerId_False() {

        when(employeeRepository
            .existsByIdAndManagerId(1L, 99L))
            .thenReturn(false);

        boolean result =
            employeeService
                .existsByIdAndManagerId(1L, 99L);

        assertFalse(result);
    }
}