package com.learning.audit.service;

import com.learning.audit.repository
       .AuditLogRepository;
import com.learning.common.dto.AuditLogResponse;
import com.learning.common.entity.AuditLog;
import com.learning.common.enums.AuditAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation
       .Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl
       implements AuditLogService {

    private final AuditLogRepository
                  auditLogRepository;
    private final ModelMapper modelMapper;

    // ── Feign API ─────────────────────────────

    @Override
    @Transactional
    public AuditLogResponse saveAuditLog(
                            AuditLog auditLog) {

        log.info("Saving audit log: {}",
                 auditLog.getAction());

        auditLogRepository.save(auditLog);

        return modelMapper.map(
               auditLog,
               AuditLogResponse.class);
    }

    // ── Admin APIs ────────────────────────────

    @Override
    public List<AuditLogResponse> getAllAuditLogs() {

        log.info("Fetching all audit logs");

        return auditLogRepository.findAll()
                .stream()
                .map(audit -> modelMapper
                              .map(audit,
                              AuditLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse>
           getAuditLogsByUser(Long userId) {

        log.info("Fetching audit logs " +
                 "for user: {}", userId);

        return auditLogRepository
                .findByPerformedByUserId(userId)
                .stream()
                .map(audit -> modelMapper
                              .map(audit,
                              AuditLogResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse>
           getAuditLogsByAction(
           AuditAction action) {

        log.info("Fetching audit logs " +
                 "for action: {}", action);

        return auditLogRepository
                .findByAction(action)
                .stream()
                .map(audit -> modelMapper
                              .map(audit,
                              AuditLogResponse.class))
                .collect(Collectors.toList());
    }
}
