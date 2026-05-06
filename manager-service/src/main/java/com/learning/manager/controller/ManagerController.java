package com.learning.manager.controller;

import com.learning.manager.service.ManagerService;
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
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    // ── Manager APIs ──────────────────────────

    @CheckHierarchy
    @GetMapping("/my-team")
    public ResponseEntity<List<EmployeeResponse>>
           getMyTeam(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   managerService
                       .getMyTeam(username),
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
                   managerService
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
                   managerService
                       .getEmployeeCertificates(
                        id, username),
                   HttpStatus.OK);
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

        managerService
            .addEmployee(request, username);

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
    @Auditable(action = AuditAction.UPDATE_EMPLOYEE)
    @PutMapping("/update-employee/{id}")
    public ResponseEntity<ApiResponseMessage>
           updateEmployee(
           @PathVariable("id") Long id,
           @Valid @RequestBody
           UserRequest request,
           @RequestHeader("username")
           String username) {

        managerService
            .updateEmployee(id, request, username);

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
    @GetMapping("/report/employee/{id}")
    public ResponseEntity<List<LearningResponse>>
           generateReportByEmployeeId(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   managerService
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
                   managerService
                       .generateReportByTeam(
                        username),
                   HttpStatus.OK);
    }

    // ── Feign APIs ────────────────────────────

    @GetMapping("/all")
    public ResponseEntity<List<ManagerResponse>>
           getAllManagers() {
        return new ResponseEntity<>(
                   managerService.getAllManagers(),
                   HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerResponse>
           getManagerById(
           @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                   managerService
                       .getManagerById(id),
                   HttpStatus.OK);
    }

    @GetMapping("/csm/{csmId}")
    public ResponseEntity<List<ManagerResponse>>
           getManagersByCsm(
           @PathVariable("csmId") Long csmId) {
        return new ResponseEntity<>(
                   managerService
                       .getManagersByCsm(csmId),
                   HttpStatus.OK);
    }

    @GetMapping("/exists/{id}/csm/{csmId}")
    public ResponseEntity<Boolean>
           existsByIdAndCsmId(
           @PathVariable("id") Long id,
           @PathVariable("csmId") Long csmId) {
        return new ResponseEntity<>(
                   managerService
                       .existsByIdAndCsmId(
                        id, csmId),
                   HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ManagerResponse>
           addManager(
           @Valid @RequestBody
           UserRequest request) {
        return new ResponseEntity<>(
                   managerService
                       .addManager(request),
                   HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ManagerResponse>
           updateManager(
           @PathVariable("id") Long id,
           @Valid @RequestBody
           UserRequest request) {
        return new ResponseEntity<>(
                   managerService
                       .updateManager(id, request),
                   HttpStatus.OK);
    }
}