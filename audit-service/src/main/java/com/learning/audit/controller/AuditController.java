package com.learning.audit.controller;

import com.learning.audit.service.AuditLogService;
import com.learning.common.dto.AuditLogResponse;
import com.learning.common.entity.AuditLog;
import com.learning.common.enums.AuditAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogService auditLogService;

    // ── Feign API ─────────────────────────────

    @PostMapping("/save")
    public ResponseEntity<AuditLogResponse>
           saveAuditLog(
           @RequestBody AuditLog auditLog) {
        return new ResponseEntity<>(
                   auditLogService
                       .saveAuditLog(auditLog),
                   HttpStatus.CREATED);
    }

    // ── Admin APIs ────────────────────────────

    @GetMapping("/all")
    public ResponseEntity<List<AuditLogResponse>>
           getAllAuditLogs() {
        return new ResponseEntity<>(
                   auditLogService.getAllAuditLogs(),
                   HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponse>>
           getAuditLogsByUser(
           @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(
                   auditLogService
                       .getAuditLogsByUser(userId),
                   HttpStatus.OK);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogResponse>>
           getAuditLogsByAction(
           @PathVariable("action") AuditAction action) {
        return new ResponseEntity<>(
                   auditLogService
                       .getAuditLogsByAction(action),
                   HttpStatus.OK);
    }
}
