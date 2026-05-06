package com.learning.learning.service;

import com.learning.learning.client.*;
import com.learning.learning.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningServiceImpl
       implements LearningService {

    private final LearningDetailsRepository
                  learningDetailsRepository;
    private final AuthClient authClient;
    private final EmployeeClient employeeClient;
    private final ManagerClient managerClient;
    private final NotificationClient
                  notificationClient;
    private final ModelMapper modelMapper;

    // ── L&D APIs ──────────────────────────────

    @Override
@Transactional
public LearningResponse updateLearningDetails(
                        Long employeeId,
                        LearningRequest request,
                        String username) {

    log.info("Updating learning " +
             "for employee: {}", employeeId);

    // AOP handles role check! ✅
    // No role check here!

    UserResponse user =
        authClient.getUserByUsername(username);

    if (user == null)
        throw new UserNotFoundException(
            "User not found!");

    EmployeeResponse employee =
        employeeClient.getEmployeeById(
            employeeId);

    if (employee == null)
        throw new EmployeeNotFoundException(
            "Employee not found!");

    LearningDetails learning =
        learningDetailsRepository
            .findFirstByEmployeeId(employeeId)
            .orElse(LearningDetails.builder()
                    .employeeId(employeeId)
                    .build());

    learning.setLearningTrack(
             request.getLearningTrack());
    learning.setTechnologies(
             request.getTechnologies());
    learning.setLearningLevel(
             request.getLearningLevel());
    learning.setModulesCompleted(
             request.getModulesCompleted());
    learning.setExamAttempts(
             request.getExamAttempts());
    learning.setLastUpdated(
             LocalDateTime.now());
    learning.setUpdatedByUserId(user.getId());

    learningDetailsRepository.save(learning);

    notificationClient
        .triggerNotification(employeeId);

    return modelMapper.map(learning,
           LearningResponse.class);
}

    // ── Feign APIs ────────────────────────────

    @Override
    public List<LearningResponse>
           getLearningByEmployee(Long employeeId) {

        log.info("Fetching learning " +
                 "for employee: {}", employeeId);

        return learningDetailsRepository
                .findByEmployeeId(employeeId)
                .stream()
                .map(l -> modelMapper
                          .map(l,
                          LearningResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LearningResponse>
           getLearningByManager(Long managerId) {

        log.info("Fetching learning " +
                 "for manager: {}", managerId);

        List<EmployeeResponse> employees =
            employeeClient
                .getEmployeesByManager(managerId);

        List<LearningDetails> allLearning =
            new ArrayList<>();

        for (EmployeeResponse employee :
             employees) {
            allLearning.addAll(
                learningDetailsRepository
                    .findByEmployeeId(
                     employee.getId()));
        }

        return allLearning.stream()
                .map(l -> modelMapper
                          .map(l,
                          LearningResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LearningResponse>
           getLearningByCsm(Long csmId) {

        log.info("Fetching learning " +
                 "for csm: {}", csmId);

        List<ManagerResponse> managers =
            managerClient.getManagersByCsm(csmId);

        List<LearningDetails> allLearning =
            new ArrayList<>();

        for (ManagerResponse manager : managers) {

            List<EmployeeResponse> employees =
                employeeClient
                    .getEmployeesByManager(
                     manager.getId());

            for (EmployeeResponse employee :
                 employees) {
                allLearning.addAll(
                    learningDetailsRepository
                        .findByEmployeeId(
                         employee.getId()));
            }
        }

        return allLearning.stream()
                .map(l -> modelMapper
                          .map(l,
                          LearningResponse.class))
                .collect(Collectors.toList());
    }
}