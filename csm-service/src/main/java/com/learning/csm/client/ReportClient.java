package com.learning.csm.client;

import com.learning.common.dto.LearningResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "report-service")
public interface ReportClient {

    @GetMapping("/report/employee/{id}")
    List<LearningResponse>
           generateReportByEmployeeId(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username); // ← Header! ✅

    @GetMapping("/report/team")
    List<LearningResponse>
           generateReportByTeam(
           @RequestHeader("username")
           String username); // ← Header! ✅
}