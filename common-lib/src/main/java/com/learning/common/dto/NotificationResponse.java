package com.learning.common.dto;

import com.learning.common.enums.NotificationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String message;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private Long employeeId;
    private Long managerId;
    private Long csmId;
}