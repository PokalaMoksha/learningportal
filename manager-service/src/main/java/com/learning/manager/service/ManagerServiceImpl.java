package com.learning.manager.service;

import com.learning.manager.client.*;
import com.learning.manager.repository.*;
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
public class ManagerServiceImpl
       implements ManagerService {

    private final ManagerRepository
                  managerRepository;
    private final AuthClient authClient;
    private final EmployeeClient employeeClient;
    private final LearningClient learningClient;
    private final CertificateClient
                  certificateClient;
    private final ReportClient reportClient;
    private final ModelMapper modelMapper;

    // ── Manager APIs ──────────────────────────

    @Override
    public List<EmployeeResponse>
           getMyTeam(String username) {

        log.info("Fetching team for: {}",
                 username);

        Manager manager =
            getManagerByUsername(username);

        return employeeClient
                .getEmployeesByManager(
                 manager.getId());
    }

    @Override
    public List<LearningResponse>
           getEmployeeLearning(
           Long employeeId,
           String username) {

        log.info("Fetching learning " +
                 "for employee: {}",
                 employeeId);

        return learningClient
                .getLearningByEmployee(
                 employeeId);
    }

    @Override
    public List<CertificateResponse>
           getEmployeeCertificates(
           Long employeeId,
           String username) {

        log.info("Fetching certificates " +
                 "for employee: {}",
                 employeeId);

        return certificateClient
                .getCertificatesByEmployee(
                 employeeId);
    }

    @Override
    @Transactional
    public EmployeeResponse addEmployee(
                            UserRequest request,
                            String username) {

        log.info("Adding employee: {}",
                 request.getUsername());

        Manager manager =
            getManagerByUsername(username);

        request.setManagerId(manager.getId());

        return employeeClient
                .addEmployee(request);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(
                            Long id,
                            UserRequest request,
                            String username) {

        log.info("Updating employee: {}", id);

        return employeeClient
                .updateEmployee(id, request);
    }

    @Override
    public List<LearningResponse>
           generateReportByEmployeeId(
           Long employeeId,
           String username) {

        log.info("Generating report " +
                 "for employee: {}",
                 employeeId);

        return reportClient
                .generateReportByEmployeeId(
                 employeeId, username);
    }

    @Override
    public List<LearningResponse>
           generateReportByTeam(
           String username) {

        log.info("Generating team report!");

        return reportClient
                .generateReportByTeam(username);
    }

    // ── Feign APIs ────────────────────────────

    @Override
    public List<ManagerResponse>
           getAllManagers() {

        log.info("Fetching all managers");

        return managerRepository
                .findAll()
                .stream()
                .map(manager -> {
                    try {
                        UserResponse user =
                            authClient
                                .getUserById(
                                 manager
                                     .getUserId());
                        return buildManagerResponse(
                               manager, user);
                    } catch (Exception e) {
                        log.warn("Could not " +
                                 "fetch user!");
                        return modelMapper.map(
                               manager,
                               ManagerResponse
                                   .class);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public ManagerResponse getManagerById(
                           Long id) {

        log.info("Fetching manager: {}", id);

        Manager manager =
            managerRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Manager not found!"));

        try {
            UserResponse user =
                authClient
                    .getUserById(
                     manager.getUserId());
            return buildManagerResponse(
                   manager, user);
        } catch (Exception e) {
            return modelMapper.map(
                   manager,
                   ManagerResponse.class);
        }
    }

    @Override
    public List<ManagerResponse>
           getManagersByCsm(Long csmId) {

        log.info("Fetching managers " +
                 "for csm: {}", csmId);

        return managerRepository
                .findByCsmId(csmId)
                .stream()
                .map(manager -> {
                    try {
                        UserResponse user =
                            authClient
                                .getUserById(
                                 manager
                                     .getUserId());
                        return buildManagerResponse(
                               manager, user);
                    } catch (Exception e) {
                        return modelMapper.map(
                               manager,
                               ManagerResponse
                                   .class);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByIdAndCsmId(
                   Long id, Long csmId) {

        return managerRepository
                .existsByIdAndCsmId(id, csmId);
    }

    @Override
    @Transactional
    public ManagerResponse addManager(
                           UserRequest request) {

        log.info("Adding manager: {}",
                 request.getUsername());

        UserResponse user =
            authClient.createUser(request);

        Manager manager = Manager.builder()
                .managerName(request.getName())
                .managerCode(request.getCode())
                .userId(user.getId())
                .csmId(request.getCsmId())
                .build();

        managerRepository.save(manager);

        return buildManagerResponse(
               manager, user);
    }

    @Override
    @Transactional
    public ManagerResponse updateManager(
                           Long id,
                           UserRequest request) {

        log.info("Updating manager: {}", id);

        Manager manager =
            managerRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Manager not found!"));

        if (request.getName() != null)
            manager.setManagerName(
                request.getName());

        if (request.getCsmId() != null)
            manager.setCsmId(
                request.getCsmId());

        managerRepository.save(manager);

        try {
            UserResponse user =
                authClient
                    .getUserById(
                     manager.getUserId());
            return buildManagerResponse(
                   manager, user);
        } catch (Exception e) {
            return modelMapper.map(
                   manager,
                   ManagerResponse.class);
        }
    }

    // ── Helpers ───────────────────────────────

    private Manager getManagerByUsername(
                    String username) {

        UserResponse user =
            authClient
                .getUserByUsername(username);

        return managerRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Manager not found!"));
    }

    private ManagerResponse
            buildManagerResponse(
            Manager manager,
            UserResponse user) {

        ManagerResponse response =
            modelMapper.map(manager,
                ManagerResponse.class);

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