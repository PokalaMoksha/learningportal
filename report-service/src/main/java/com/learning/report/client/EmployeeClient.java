package com.learning.report.client;

import com.learning.common.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employee/{id}")
    EmployeeResponse getEmployeeById(
        @PathVariable("id") Long id);

    @GetMapping("/employee/exists/{id}/manager/{managerId}")
    boolean existsByIdAndManagerId(
        @PathVariable("id") Long id,
        @PathVariable("managerId") Long managerId);

    @GetMapping("/employee/manager/{managerId}")
    List<EmployeeResponse> getEmployeesByManager(
        @PathVariable("managerId") Long managerId);
}
