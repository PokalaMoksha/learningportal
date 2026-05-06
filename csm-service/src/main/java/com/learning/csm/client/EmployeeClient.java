package com.learning.csm.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employee/manager/{managerId}")
    List<EmployeeResponse> getEmployeesByManager(
        @PathVariable("managerId") Long managerId);

    @GetMapping("/employee/{id}")
    EmployeeResponse getEmployeeById(
        @PathVariable("id") Long id);

    @PostMapping("/employee/add")
    EmployeeResponse addEmployee(
        @RequestBody UserRequest request);

    @PutMapping("/employee/update/{id}")
    EmployeeResponse updateEmployee(
        @PathVariable("id") Long id,
        @RequestBody UserRequest request);

    @GetMapping("/employee/exists/{id}/manager/{managerId}")
    boolean existsByIdAndManagerId(
        @PathVariable("id") Long id,
        @PathVariable("managerId") Long managerId);
}
