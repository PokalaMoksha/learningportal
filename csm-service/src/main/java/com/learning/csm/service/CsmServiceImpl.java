package com.learning.csm.service;

import com.learning.csm.client.*;
import com.learning.csm.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsmServiceImpl
       implements CsmService {

    private final CsmRepository csmRepository;
    private final TeamHierarchyRepository
                  teamHierarchyRepository;
    private final AuthClient authClient;
    private final EmployeeClient employeeClient;
    private final ManagerClient managerClient;
    private final LearningClient learningClient;
    private final CertificateClient
                  certificateClient;
    private final ReportClient reportClient;
    private final ModelMapper modelMapper;

    // ── CSM APIs ──────────────────────────────

    @Override
    public List<ManagerResponse>
           getMyManagers(String username) {

        log.info("Fetching managers for: {}",
                 username);

        Csm csm = getCsmByUsername(username);

        return managerClient
                .getManagersByCsm(csm.getId());
    }

    @Override
    public List<EmployeeResponse>
           getMyTeams(String username) {

        log.info("Fetching teams for: {}",
                 username);

        Csm csm = getCsmByUsername(username);

        List<ManagerResponse> managers =
            managerClient
                .getManagersByCsm(csm.getId());

        List<EmployeeResponse> employees =
            new ArrayList<>();

        for (ManagerResponse manager :
             managers) {
            employees.addAll(
                employeeClient
                    .getEmployeesByManager(
                     manager.getId()));
        }

        return employees;
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
    public ManagerResponse addManager(
                           UserRequest request,
                           String username) {

        log.info("Adding manager: {}",
                 request.getUsername());

        Csm csm = getCsmByUsername(username);

        request.setCsmId(csm.getId());

        return managerClient
                .addManager(request);
    }

    @Override
    @Transactional
    public EmployeeResponse addEmployee(
                            UserRequest request,
                            String username) {

        log.info("Adding employee: {}",
                 request.getUsername());

        Csm csm = getCsmByUsername(username);

        EmployeeResponse employee =
            employeeClient.addEmployee(request);

        TeamHierarchy hierarchy =
            TeamHierarchy.builder()
                .csmId(csm.getId())
                .managerId(request.getManagerId())
                .employeeId(employee.getId())
                .isActive(true)
                .effectiveFrom(LocalDate.now())
                .build();

        teamHierarchyRepository.save(hierarchy);

        return employee;
    }

    @Override
    @Transactional
    public ManagerResponse updateManager(
                           Long managerId,
                           UserRequest request,
                           String username) {

        log.info("Updating manager: {}",
                 managerId);

        return managerClient
                .updateManager(managerId,
                 request);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(
                            Long employeeId,
                            UserRequest request,
                            String username) {

        log.info("Updating employee: {}",
                 employeeId);

        return employeeClient
                .updateEmployee(
                 employeeId, request);
    }

    @Override
    @Transactional
    public void reassignEmployee(
                ReassignEmployeeRequest request,
                String username) {

        log.info("Reassigning employee: {}",
                 request.getEmployeeId());

        UserRequest updateRequest =
            UserRequest.builder()
                .managerId(
                 request.getNewManagerId())
                .build();

        employeeClient.updateEmployee(
            request.getEmployeeId(),
            updateRequest);

        TeamHierarchy hierarchy =
            teamHierarchyRepository
                .findByEmployeeId(
                 request.getEmployeeId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Hierarchy not found!"));

        hierarchy.setManagerId(
            request.getNewManagerId());
        hierarchy.setEffectiveFrom(
            LocalDate.now());

        teamHierarchyRepository.save(hierarchy);
    }

    @Override
    @Transactional
    public void reassignManager(
                ReassignManagerRequest request,
                String username) {

        log.info("Reassigning manager: {}",
                 request.getManagerId());

        UserRequest updateRequest =
            UserRequest.builder()
                .csmId(request.getNewCsmId())
                .build();

        managerClient.updateManager(
            request.getManagerId(),
            updateRequest);

        List<TeamHierarchy> hierarchies =
            teamHierarchyRepository
                .findByManagerId(
                 request.getManagerId());

        for (TeamHierarchy hierarchy :
             hierarchies) {
            hierarchy.setCsmId(
                request.getNewCsmId());
            hierarchy.setEffectiveFrom(
                LocalDate.now());
            teamHierarchyRepository
                .save(hierarchy);
        }
    }

    // ── Report APIs ───────────────────────────

    @Override
    public List<LearningResponse>
           generateReportByEmployeeId(
           Long employeeId,
           String username) {

        return reportClient
                .generateReportByEmployeeId(
                 employeeId, username);
    }

    @Override
    public List<LearningResponse>
           generateReportByTeam(
           String username) {

        return reportClient
                .generateReportByTeam(username);
    }

    // ── Feign APIs ────────────────────────────

    @Override
    public List<CsmResponse> getAllCsms() {

        log.info("Fetching all CSMs");

        return csmRepository.findAll()
                .stream()
                .map(csm -> {
                    try {
                        UserResponse user =
                            authClient
                                .getUserById(
                                 csm.getUserId());
                        return buildCsmResponse(
                               csm, user);
                    } catch (Exception e) {
                        return modelMapper.map(
                               csm,
                               CsmResponse.class);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public CsmResponse getCsmById(Long id) {

        log.info("Fetching CSM: {}", id);

        Csm csm = csmRepository
                    .findById(id)
                    .orElseThrow(() ->
                        new ResourceNotFoundException(
                        "CSM not found!"));

        try {
            UserResponse user =
                authClient
                    .getUserById(csm.getUserId());
            return buildCsmResponse(csm, user);
        } catch (Exception e) {
            return modelMapper.map(
                   csm, CsmResponse.class);
        }
    }

    @Override
    @Transactional
    public CsmResponse addCsm(
                       UserRequest request) {

        log.info("Adding CSM: {}",
                 request.getUsername());

        UserResponse user =
            authClient.createUser(request);

        Csm csm = Csm.builder()
                .csmName(request.getName())
                .csmCode(request.getCode())
                .userId(user.getId())
                .build();

        csmRepository.save(csm);

        return buildCsmResponse(csm, user);
    }

    @Override
    public TeamHierarchyResponse
           getHierarchyByEmployee(
           Long employeeId) {

        log.info("Fetching hierarchy " +
                 "for employee: {}",
                 employeeId);

        TeamHierarchy hierarchy =
            teamHierarchyRepository
                .findByEmployeeId(employeeId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Hierarchy not found!"));

        return modelMapper.map(
               hierarchy,
               TeamHierarchyResponse.class);
    }

    // ── Helpers ───────────────────────────────

    private Csm getCsmByUsername(
                String username) {

        UserResponse user =
            authClient
                .getUserByUsername(username);

        return csmRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                    new CsmNotFoundException(
                    "CSM not found!"));
    }

    private CsmResponse buildCsmResponse(
                         Csm csm,
                         UserResponse user) {

        CsmResponse response =
            modelMapper.map(csm,
                CsmResponse.class);

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