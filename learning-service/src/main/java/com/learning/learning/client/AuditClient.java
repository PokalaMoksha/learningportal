package com.learning.learning.client;

import com.learning.common.dto.AuditLogResponse;
import com.learning.common.entity.AuditLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "audit-service")
public interface AuditClient {

    @PostMapping("/audit/save")
    AuditLogResponse saveAuditLog(
        @RequestBody AuditLog auditLog);
}
