package com.learning.employee.client;

import com.learning.common.dto.LearningResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "learning-service")
public interface LearningClient {

    @GetMapping("/learning/employee/{employeeId}")
    List<LearningResponse> getLearningByEmployee(
        @PathVariable("employeeId") Long employeeId);
}
