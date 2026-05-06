package com.learning.audit.repository;

import com.learning.common.entity.AuditLog;
import com.learning.common.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository
       extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByPerformedByUserId(
                   Long userId);

    List<AuditLog> findByAction(
                   AuditAction action);
}
