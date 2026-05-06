package com.learning.notification.client;

import com.learning.common.dto.ManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "manager-service")
public interface ManagerClient {

    @GetMapping("/manager/{id}")
    ManagerResponse getManagerById(
        @PathVariable("id") Long id);
}
