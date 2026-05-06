package com.learning.notification.controller;

import com.learning.notification.service
       .JobTriggerService;
import com.learning.common.response
       .ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final JobTriggerService
                  jobTriggerService;

    @PostMapping("/trigger")
    public ResponseEntity<ApiResponseMessage>
           triggerNotification(
           @RequestParam("employeeId") Long employeeId) {

        jobTriggerService
            .triggerNotificationJob(employeeId);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Notification triggered!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }
}
