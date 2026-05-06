package com.learning.notification.batch;

import com.learning.common.dto.*;
import com.learning.common.entity.NotificationLog;
import com.learning.notification.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationItemProcessor
       implements ItemProcessor<NotificationLog, NotificationLog> {

    private final EmployeeClient employeeClient;
    private final CsmClient csmClient;

    @Override
    public NotificationLog process(
           NotificationLog notification)
           throws Exception {

        log.info("Processing notification " +
                 "for employee: {}",
                 notification.getEmployeeId());

        try {
            EmployeeResponse employee =
                employeeClient.getEmployeeById(
                    notification.getEmployeeId());

            TeamHierarchyResponse hierarchy =
    csmClient
        .getHierarchyByEmployee(
         notification.getEmployeeId());

            notification.setManagerId(
                hierarchy.getManagerId());
            notification.setCsmId(
                hierarchy.getCsmId());

            String message =
                "Learning details updated " +
                "for employee: " +
                employee.getEmployeeName();

            notification.setMessage(message);

            log.info("Notification processed!");

            return notification;

        } catch (Exception e) {
            log.error("Processing failed: {}",
                      e.getMessage());
            return null;
        }
    }
}