package com.learning.admin.service;

import com.learning.admin.client.*;
import com.learning.admin.repository.*;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.enums.Role;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl
       implements AdminService {

    private final TemporaryAccessRepository
                  temporaryAccessRepository;
    private final AuthClient authClient;
    private final EmployeeClient employeeClient;
    private final ManagerClient managerClient;
    private final CsmClient csmClient;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserResponse addUser(
                       UserRequest request) {

        log.info("Adding new user: {}",
                 request.getUsername());

        Role role = Role.valueOf(
                    request.getRole()
                           .toUpperCase());

        switch (role) {
            case EMPLOYEE -> {
                employeeClient
                    .addEmployee(request);
                log.info("Employee added: {}",
                         request.getUsername());
            }
            case MANAGER -> {
                managerClient
                    .addManager(request);
                log.info("Manager added: {}",
                         request.getUsername());
            }
            case CSM -> {
                csmClient.addCsm(request);
                log.info("CSM added: {}",
                         request.getUsername());
            }
            case LD, ADMIN -> {
                authClient.createUser(request);
                log.info("{} added: {}",
                         role,
                         request.getUsername());
            }
        }

        return UserResponse.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(role)
                .isActive(true)
                .build();
    }

    @Override
    @Transactional
    public TemporaryAccessResponse
           grantTemporaryAccess(
           TemporaryAccessRequest request) {

        log.info("Granting access to CSM: {}",
                 request.getCsmId());

        if (request.getCsmId()
                   .equals(
                    request.getTargetCsmId()))
            throw new BadApiRequest(
                "Cannot grant access " +
                "to own teams!");

        if (request.getEndDate()
                   .isBefore(
                    request.getStartDate()))
            throw new BadApiRequest(
                "End date cannot be " +
                "before start date!");

        TemporaryAccess access =
            TemporaryAccess.builder()
                .csmId(request.getCsmId())
                .targetCsmId(
                 request.getTargetCsmId())
                .startDate(
                 request.getStartDate())
                .endDate(request.getEndDate())
                .isActive(true)
                .build();

        temporaryAccessRepository.save(access);

        log.info("Access granted!");

        return modelMapper.map(access,
               TemporaryAccessResponse.class);
    }

    @Override
    @Transactional
    public void revokeTemporaryAccess(
                Long accessId) {

        log.info("Revoking access: {}",
                 accessId);

        TemporaryAccess access =
            temporaryAccessRepository
                .findById(accessId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Access not found!"));

        access.setActive(false);
        temporaryAccessRepository.save(access);

        log.info("Access revoked: {}",
                 accessId);
    }

    @Override
    public List<EmployeeResponse>
           getAllEmployees() {
        log.info("Fetching all employees");
        return employeeClient.getAllEmployees();
    }

    @Override
    public List<ManagerResponse>
           getAllManagers() {
        log.info("Fetching all managers");
        return managerClient.getAllManagers();
    }

    @Override
    public List<CsmResponse> getAllCsms() {
        log.info("Fetching all CSMs");
        return csmClient.getAllCsms();
    }
}