package com.learning.audit.service;

import com.learning.common.dto.AuditLogResponse;
import com.learning.common.entity.AuditLog;
import com.learning.common.enums.AuditAction;
import java.util.List;

public interface AuditLogService {

    // ── Feign API ─────────────────────────────
    // Called by AOP in other services!

    AuditLogResponse saveAuditLog(
                     AuditLog auditLog);

    // ── Admin APIs ────────────────────────────

    List<AuditLogResponse> getAllAuditLogs();

    List<AuditLogResponse> getAuditLogsByUser(
                           Long userId);

    List<AuditLogResponse> getAuditLogsByAction(
                           AuditAction action);
}
