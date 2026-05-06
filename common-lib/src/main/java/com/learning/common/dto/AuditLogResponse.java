package com.learning.common.dto;

import com.learning.common.enums.AuditAction;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;
    private AuditAction action;
    private String entityType;
    private Long entityId;
    private String oldValue;
    private String newValue;
    private LocalDateTime performedAt;
    private String ipAddress;
    private Long performedByUserId;
}