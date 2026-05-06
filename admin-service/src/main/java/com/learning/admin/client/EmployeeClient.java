package com.learning.admin.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/employee/all")
    List<EmployeeResponse> getAllEmployees();

    @PostMapping("/employee/add")
    EmployeeResponse addEmployee(
        @RequestBody UserRequest request);
}
