package com.learning.csm.client;

import com.learning.common.dto.LearningResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "learning-service")
public interface LearningClient {

    @GetMapping("/learning/employee/{employeeId}")
    List<LearningResponse> getLearningByEmployee(
        @PathVariable("employeeId") Long employeeId);

    @GetMapping("/learning/manager/{managerId}")
    List<LearningResponse> getLearningByManager(
        @PathVariable("managerId") Long managerId);

    @GetMapping("/learning/csm/{csmId}")
    List<LearningResponse> getLearningByCsm(
        @PathVariable("csmId") Long csmId);
}
