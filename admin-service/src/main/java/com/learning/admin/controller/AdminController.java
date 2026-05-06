package com.learning.admin.controller;

import com.learning.admin.service.AdminService;
import com.learning.common.annotation.Auditable;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Auditable(action = AuditAction.ADD_USER)
    @PostMapping("/add-user")
    public ResponseEntity<ApiResponseMessage>
           addUser(
           @Valid @RequestBody
           UserRequest request) {

        adminService.addUser(request);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("User added!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.CREATED);
    }

    @Auditable(action = AuditAction.GRANT_TEMP_ACCESS)
    @PostMapping("/grant-access")
    public ResponseEntity<ApiResponseMessage>
           grantTemporaryAccess(
           @Valid @RequestBody
           TemporaryAccessRequest request) {

        adminService
            .grantTemporaryAccess(request);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Access granted!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @Auditable(action = AuditAction.REVOKE_TEMP_ACCESS)
    @PutMapping("/revoke-access/{accessId}")
    public ResponseEntity<ApiResponseMessage>
           revokeTemporaryAccess(
           @PathVariable("accessId") Long accessId) {

        adminService
            .revokeTemporaryAccess(accessId);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Access revoked!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>>
           getAllEmployees() {
        return new ResponseEntity<>(
                   adminService.getAllEmployees(),
                   HttpStatus.OK);
    }

    @GetMapping("/managers")
    public ResponseEntity<List<ManagerResponse>>
           getAllManagers() {
        return new ResponseEntity<>(
                   adminService.getAllManagers(),
                   HttpStatus.OK);
    }

    @GetMapping("/csms")
    public ResponseEntity<List<CsmResponse>>
           getAllCsms() {
        return new ResponseEntity<>(
                   adminService.getAllCsms(),
                   HttpStatus.OK);
    }
}