package com.learning.notification.client;

import com.learning.common.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employee/{id}")
    EmployeeResponse getEmployeeById(
        @PathVariable("id") Long id);
}
