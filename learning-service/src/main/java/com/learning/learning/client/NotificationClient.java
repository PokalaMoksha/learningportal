package com.learning.learning.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/notification/trigger")
    void triggerNotification(
        @RequestParam("employeeId") Long employeeId);
}
