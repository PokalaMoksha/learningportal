package com.learning.employee.service;

import com.learning.employee.client.*;
import com.learning.employee.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl
       implements EmployeeService {

    private final EmployeeRepository
                  employeeRepository;
    private final AuthClient authClient;
    private final ManagerClient managerClient;
    private final LearningClient learningClient;
    private final CertificateClient
                  certificateClient;
    private final ModelMapper modelMapper;

    // ── Employee APIs ─────────────────────────

    @Override
    public EmployeeResponse getMyDetails(
                            String username) {

        log.info("Fetching details for: {}",
                 username);

        UserResponse user =
            authClient
                .getUserByUsername(username);

        Employee employee =
            employeeRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        return buildEmployeeResponse(
               employee, user);
    }

    @Override
    public List<LearningResponse>
           getMyLearning(String username) {

        log.info("Fetching learning for: {}",
                 username);

        UserResponse user =
            authClient
                .getUserByUsername(username);

        Employee employee =
            employeeRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        return learningClient
                .getLearningByEmployee(
                 employee.getId());
    }

    @Override
    public ManagerResponse getMyManager(
                           String username) {

        log.info("Fetching manager for: {}",
                 username);

        UserResponse user =
            authClient
                .getUserByUsername(username);

        Employee employee =
            employeeRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        return managerClient
                .getManagerById(
                 employee.getManagerId());
    }

    @Override
    public List<CertificateResponse>
           getMyCertificates(String username) {

        log.info("Fetching certificates for: {}",
                 username);

        UserResponse user =
            authClient
                .getUserByUsername(username);

        Employee employee =
            employeeRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        return certificateClient
                .getCertificatesByEmployee(
                 employee.getId());
    }

    // ── Feign APIs ────────────────────────────

    @Override
    public List<EmployeeResponse>
           getAllEmployees() {

        log.info("Fetching all employees");

        return employeeRepository
                .findAll()
                .stream()
                .map(employee -> {
                    try {
                        UserResponse user =
                            authClient
                                .getUserById(
                                 employee
                                     .getUserId());
                        return buildEmployeeResponse(
                               employee, user);
                    } catch (Exception e) {
                        log.warn("Could not " +
                                 "fetch user: {}",
                                 e.getMessage());
                        return modelMapper.map(
                               employee,
                               EmployeeResponse
                                   .class);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(
                            Long id) {

        log.info("Fetching employee: {}", id);

        Employee employee =
            employeeRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        try {
            UserResponse user =
                authClient
                    .getUserById(
                     employee.getUserId());
            return buildEmployeeResponse(
                   employee, user);
        } catch (Exception e) {
            log.warn("Could not fetch user!");
            return modelMapper.map(
                   employee,
                   EmployeeResponse.class);
        }
    }

    @Override
    public List<EmployeeResponse>
           getEmployeesByManager(
           Long managerId) {

        log.info("Fetching employees " +
                 "for manager: {}", managerId);

        return employeeRepository
                .findByManagerId(managerId)
                .stream()
                .map(employee -> {
                    try {
                        UserResponse user =
                            authClient
                                .getUserById(
                                 employee
                                     .getUserId());
                        return buildEmployeeResponse(
                               employee, user);
                    } catch (Exception e) {
                        log.warn("Could not " +
                                 "fetch user!");
                        return modelMapper.map(
                               employee,
                               EmployeeResponse
                                   .class);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIdAndManagerId(
                   Long id, Long managerId) {

        return employeeRepository
                .existsByIdAndManagerId(
                 id, managerId);
    }

    @Override
    @Transactional
    public EmployeeResponse addEmployee(
                            UserRequest request) {

        log.info("Adding employee: {}",
                 request.getUsername());

        UserResponse user =
            authClient.createUser(request);

        Employee employee = Employee.builder()
                .employeeName(request.getName())
                .employeeCode(request.getCode())
                .userId(user.getId())
                .managerId(request.getManagerId())
                .build();

        employeeRepository.save(employee);

        return buildEmployeeResponse(
               employee, user);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(
                            Long id,
                            UserRequest request) {

        log.info("Updating employee: {}", id);

        Employee employee =
            employeeRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Employee not found!"));

        if (request.getName() != null)
            employee.setEmployeeName(
                request.getName());

        if (request.getManagerId() != null)
            employee.setManagerId(
                request.getManagerId());

        employeeRepository.save(employee);

        try {
            UserResponse user =
                authClient
                    .getUserById(
                     employee.getUserId());
            return buildEmployeeResponse(
                   employee, user);
        } catch (Exception e) {
            return modelMapper.map(
                   employee,
                   EmployeeResponse.class);
        }
    }

    // ── Helper ────────────────────────────────

    private EmployeeResponse
            buildEmployeeResponse(
            Employee employee,
            UserResponse user) {

        EmployeeResponse response =
            modelMapper.map(employee,
                EmployeeResponse.class);

        response.setUsername(
            user.getUsername());
        response.setEmail(
            user.getEmail());
        response.setRole(
            user.getRole().name());
        response.setIsActive(
       user.getIsActive());
        return response;
    }
}