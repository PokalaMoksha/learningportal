package com.learning.notification.batch;

import com.learning.common.dto.*;
import com.learning.common.entity.NotificationLog;
import com.learning.common.enums.NotificationStatus;
import com.learning.notification.client.*;
import com.learning.notification.repository
       .NotificationLogRepository;
import com.learning.notification.service
       .EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationItemWriter
       implements ItemWriter<NotificationLog> {

    private final EmailService emailService;
    private final ManagerClient managerClient;
    private final CsmClient csmClient;
    private final NotificationLogRepository
                  notificationLogRepository;

    @Override
    public void write(
           Chunk<? extends NotificationLog>
           chunk) throws Exception {

        for (NotificationLog notification :
             chunk.getItems()) {

            try {
                // Get manager email
                ManagerResponse manager =
                    managerClient.getManagerById(
                        notification.getManagerId());

                // Get CSM email
                CsmResponse csm =
                    csmClient.getCsmById(
                        notification.getCsmId());

                // Send to manager!
                emailService.sendEmail(
                    manager.getEmail(),
                    "Learning Update Notification",
                    notification.getMessage());

                // Send to CSM!
                emailService.sendEmail(
                    csm.getEmail(),
                    "Learning Update Notification",
                    notification.getMessage());

                notification.setStatus(
                    NotificationStatus.SENT);
                notification.setSentAt(
                    LocalDateTime.now());

                log.info("Notification sent!");

            } catch (Exception e) {
                notification.setStatus(
                    NotificationStatus.FAILED);
                log.error("Notification failed: {}",
                          e.getMessage());
            }

            notificationLogRepository
                .save(notification);
        }
    }
}
