package com.learning.notification.service;

import com.learning.common.entity.NotificationLog;
import com.learning.common.enums.NotificationStatus;
import com.learning.notification.repository
       .NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch
       .JobLauncher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobTriggerService {

    private final JobLauncher jobLauncher;
    private final Job notificationJob;
    private final NotificationLogRepository
                  notificationLogRepository;

    public void triggerNotificationJob(
                Long employeeId) {

        log.info("Triggering notification " +
                 "job for employee: {}",
                 employeeId);

        try {
            // Changed variable name from
            // log to notificationLog! ✅
            NotificationLog notificationLog =
                NotificationLog.builder()
                    .employeeId(employeeId)
                    .status(
                     NotificationStatus.PENDING)
                    .message("")
                    .managerId(0L)
                    .csmId(0L)
                    .build();

            notificationLogRepository
                .save(notificationLog);

            JobParameters params =
                new JobParametersBuilder()
                    .addLong("employeeId",
                             employeeId)
                    .addLong("time",
                     System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(
                notificationJob, params);

            log.info("Notification job " +
                     "triggered!");

        } catch (Exception e) {
            log.error("Job trigger failed: {}",
                      e.getMessage());
        }
    }
}