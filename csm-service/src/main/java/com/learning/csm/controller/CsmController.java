package com.learning.csm.controller;

import com.learning.csm.service.CsmService;
import com.learning.common.annotation.Auditable;
import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.dto.*;
import com.learning.common.enums.AuditAction;
import com.learning.common.response.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/csm")
@RequiredArgsConstructor
public class CsmController {

    private final CsmService csmService;

    // ── CSM APIs ──────────────────────────────

    @CheckHierarchy
    @GetMapping("/my-managers")
    public ResponseEntity<List<ManagerResponse>>
           getMyManagers(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService
                       .getMyManagers(username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/my-teams")
    public ResponseEntity<List<EmployeeResponse>>
           getMyTeams(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService.getMyTeams(username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/employee/{id}/learning")
    public ResponseEntity<List<LearningResponse>>
           getEmployeeLearning(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService
                       .getEmployeeLearning(
                        id, username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/employee/{id}/certificates")
    public ResponseEntity<List<CertificateResponse>>
           getEmployeeCertificates(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService
                       .getEmployeeCertificates(
                        id, username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.ADD_MANAGER)
    @PostMapping("/add-manager")
    public ResponseEntity<ApiResponseMessage>
           addManager(
           @Valid @RequestBody
           UserRequest request,
           @RequestHeader("username")
           String username) {

        csmService.addManager(request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Manager added!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.CREATED);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.ADD_EMPLOYEE)
    @PostMapping("/add-employee")
    public ResponseEntity<ApiResponseMessage>
           addEmployee(
           @Valid @RequestBody
           UserRequest request,
           @RequestHeader("username")
           String username) {

        csmService.addEmployee(request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Employee added!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.CREATED);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.UPDATE_MANAGER)
    @PutMapping("/update-manager/{id}")
    public ResponseEntity<ApiResponseMessage>
           updateManager(
           @PathVariable("id") Long id,
           @Valid @RequestBody
           UserRequest request,
           @RequestHeader("username")
           String username) {

        csmService.updateManager(
            id, request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Manager updated!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.UPDATE_EMPLOYEE)
    @PutMapping("/update-employee/{id}")
    public ResponseEntity<ApiResponseMessage>
           updateEmployee(
           @PathVariable("id") Long id,
           @Valid @RequestBody
           UserRequest request,
           @RequestHeader("username")
           String username) {

        csmService.updateEmployee(
            id, request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Employee updated!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.REASSIGN_EMPLOYEE)
    @PostMapping("/reassign-employee")
    public ResponseEntity<ApiResponseMessage>
           reassignEmployee(
           @Valid @RequestBody
           ReassignEmployeeRequest request,
           @RequestHeader("username")
           String username) {

        csmService.reassignEmployee(
            request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Employee reassigned!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @Auditable(action = AuditAction.REASSIGN_MANAGER)
    @PostMapping("/reassign-manager")
    public ResponseEntity<ApiResponseMessage>
           reassignManager(
           @Valid @RequestBody
           ReassignManagerRequest request,
           @RequestHeader("username")
           String username) {

        csmService.reassignManager(
            request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Manager reassigned!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/report/employee/{id}")
    public ResponseEntity<List<LearningResponse>>
           generateReportByEmployeeId(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService
                       .generateReportByEmployeeId(
                        id, username),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/report/team")
    public ResponseEntity<List<LearningResponse>>
           generateReportByTeam(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   csmService
                       .generateReportByTeam(
                        username),
                   HttpStatus.OK);
    }

    // ── Feign APIs ────────────────────────────

    @GetMapping("/all")
    public ResponseEntity<List<CsmResponse>>
           getAllCsms() {
        return new ResponseEntity<>(
                   csmService.getAllCsms(),
                   HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CsmResponse>
           getCsmById(
           @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                   csmService.getCsmById(id),
                   HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CsmResponse>
           addCsm(
           @Valid @RequestBody
           UserRequest request) {
        return new ResponseEntity<>(
                   csmService.addCsm(request),
                   HttpStatus.CREATED);
    }

    @GetMapping("/hierarchy/employee/{employeeId}")
    public ResponseEntity<TeamHierarchyResponse>
           getHierarchyByEmployee(
           @PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(
                   csmService
                       .getHierarchyByEmployee(
                        employeeId),
                   HttpStatus.OK);
    }
}