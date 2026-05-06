package com.learning.learning.service;

import com.learning.learning.client.*;
import com.learning.learning.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.enums.*;
import com.learning.common.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningServiceImplTest {

    @Mock
    private LearningDetailsRepository
            learningDetailsRepository;

    @Mock
    private AuthClient authClient;

    @Mock
    private EmployeeClient employeeClient;

    @Mock
    private ManagerClient managerClient;

    @Mock
    private NotificationClient
            notificationClient;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LearningServiceImpl
            learningService;

    private LearningDetails learningDetails;
    private LearningResponse learningResponse;
    private UserResponse userResponse;
    private EmployeeResponse employeeResponse;
    private ManagerResponse managerResponse;

    @BeforeEach
    void setUp() {

        learningDetails =
            LearningDetails.builder()
                .id(1L)
                .employeeId(1L)
                .learningTrack(
                    LearningTrack.BACKEND)
                .technologies(
                    "Java, Spring Boot")
                .learningLevel(
                    LearningLevel.INTERMEDIATE)
                .modulesCompleted(8)
                .examAttempts(2)
                .lastUpdated(LocalDateTime.now())
                .updatedByUserId(1L)
                .build();

        learningResponse =
            LearningResponse.builder()
                .id(1L)
                .employeeId(1L)
                .learningTrack(
                    LearningTrack.BACKEND)
                .technologies(
                    "Java, Spring Boot")
                .learningLevel(
                    LearningLevel.INTERMEDIATE)
                .modulesCompleted(8)
                .examAttempts(2)
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .username("dhana")
                .email("dhana@learning.com")
                .role(Role.LD)
                .isActive(true)
                .build();

        employeeResponse =
            EmployeeResponse.builder()
                .id(1L)
                .employeeName("moksha")
                .employeeCode("EMP001")
                .build();

        managerResponse =
            ManagerResponse.builder()
                .id(1L)
                .managerName("abdul")
                .build();
    }

    // ── updateLearningDetails Tests ───────────

    @Test
    void updateLearningDetails_Success() {

        LearningRequest request =
            LearningRequest.builder()
                .learningTrack(
                    LearningTrack.BACKEND)
                .technologies(
                    "Java, Spring Boot")
                .learningLevel(
                    LearningLevel.INTERMEDIATE)
                .modulesCompleted(8)
                .examAttempts(2)
                .build();

        when(authClient
            .getUserByUsername("dhana"))
            .thenReturn(userResponse);

        when(employeeClient
            .getEmployeeById(1L))
            .thenReturn(employeeResponse);

        when(learningDetailsRepository
            .findFirstByEmployeeId(1L))
            .thenReturn(
                Optional.of(learningDetails));

        when(learningDetailsRepository
            .save(any(LearningDetails.class)))
            .thenReturn(learningDetails);

        when(modelMapper.map(
            any(LearningDetails.class),
            eq(LearningResponse.class)))
            .thenReturn(learningResponse);

        doNothing().when(notificationClient)
            .triggerNotification(1L);

        LearningResponse result =
            learningService
                .updateLearningDetails(
                 1L, request, "dhana");

        assertNotNull(result);
        assertEquals(
            LearningTrack.BACKEND,
            result.getLearningTrack());
        assertEquals(8,
            result.getModulesCompleted());

        verify(learningDetailsRepository)
            .save(any(LearningDetails.class));
        verify(notificationClient)
            .triggerNotification(1L);
    }

    @Test
    void updateLearningDetails_NewLearning() {

        LearningRequest request =
            LearningRequest.builder()
                .learningTrack(
                    LearningTrack.FRONTEND)
                .technologies("React")
                .learningLevel(
                    LearningLevel.BEGINNER)
                .modulesCompleted(3)
                .examAttempts(1)
                .build();

        when(authClient
            .getUserByUsername("dhana"))
            .thenReturn(userResponse);

        when(employeeClient
            .getEmployeeById(1L))
            .thenReturn(employeeResponse);

        when(learningDetailsRepository
            .findFirstByEmployeeId(1L))
            .thenReturn(Optional.empty());

        when(learningDetailsRepository
            .save(any(LearningDetails.class)))
            .thenReturn(learningDetails);

        when(modelMapper.map(
            any(LearningDetails.class),
            eq(LearningResponse.class)))
            .thenReturn(learningResponse);

        doNothing().when(notificationClient)
            .triggerNotification(1L);

        LearningResponse result =
            learningService
                .updateLearningDetails(
                 1L, request, "dhana");

        assertNotNull(result);
        verify(learningDetailsRepository)
            .save(any(LearningDetails.class));
    }

    @Test
    void updateLearningDetails_UserNotFound() {

        LearningRequest request =
            LearningRequest.builder()
                .learningTrack(
                    LearningTrack.BACKEND)
                .build();

        when(authClient
            .getUserByUsername("unknown"))
            .thenThrow(new
                UserNotFoundException(
                "User not found!"));

        assertThrows(
            UserNotFoundException.class,
            () -> learningService
                      .updateLearningDetails(
                       1L, request, "unknown"));
    }

    @Test
    void updateLearningDetails_EmployeeNotFound() {

        LearningRequest request =
            LearningRequest.builder()
                .learningTrack(
                    LearningTrack.BACKEND)
                .build();

        when(authClient
            .getUserByUsername("dhana"))
            .thenReturn(userResponse);

        when(employeeClient
            .getEmployeeById(99L))
            .thenThrow(new
                ResourceNotFoundException(
                "Employee not found!"));

        assertThrows(
            ResourceNotFoundException.class,
            () -> learningService
                      .updateLearningDetails(
                       99L, request, "dhana"));
    }

    // ── getLearningByEmployee Tests ───────────

    @Test
    void getLearningByEmployee_Success() {

        when(learningDetailsRepository
            .findByEmployeeId(1L))
            .thenReturn(
                Arrays.asList(learningDetails));

        when(modelMapper.map(
            learningDetails,
            LearningResponse.class))
            .thenReturn(learningResponse);

        List<LearningResponse> result =
            learningService
                .getLearningByEmployee(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(
            LearningTrack.BACKEND,
            result.get(0).getLearningTrack());
    }

    @Test
    void getLearningByEmployee_Empty() {

        when(learningDetailsRepository
            .findByEmployeeId(99L))
            .thenReturn(List.of());

        List<LearningResponse> result =
            learningService
                .getLearningByEmployee(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ── getLearningByManager Tests ────────────

    @Test
    void getLearningByManager_Success() {

        when(employeeClient
            .getEmployeesByManager(1L))
            .thenReturn(
                Arrays.asList(employeeResponse));

        when(learningDetailsRepository
            .findByEmployeeId(1L))
            .thenReturn(
                Arrays.asList(learningDetails));

        when(modelMapper.map(
            learningDetails,
            LearningResponse.class))
            .thenReturn(learningResponse);

        List<LearningResponse> result =
            learningService
                .getLearningByManager(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getLearningByManager_NoEmployees() {

        when(employeeClient
            .getEmployeesByManager(99L))
            .thenReturn(List.of());

        List<LearningResponse> result =
            learningService
                .getLearningByManager(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ── getLearningByCsm Tests ────────────────

    @Test
    void getLearningByCsm_Success() {

        when(managerClient
            .getManagersByCsm(1L))
            .thenReturn(
                Arrays.asList(managerResponse));

        when(employeeClient
            .getEmployeesByManager(1L))
            .thenReturn(
                Arrays.asList(employeeResponse));

        when(learningDetailsRepository
            .findByEmployeeId(1L))
            .thenReturn(
                Arrays.asList(learningDetails));

        when(modelMapper.map(
            learningDetails,
            LearningResponse.class))
            .thenReturn(learningResponse);

        List<LearningResponse> result =
            learningService
                .getLearningByCsm(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void getLearningByCsm_NoManagers() {

        when(managerClient
            .getManagersByCsm(99L))
            .thenReturn(List.of());

        List<LearningResponse> result =
            learningService
                .getLearningByCsm(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
