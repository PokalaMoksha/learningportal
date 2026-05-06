package com.learning.report.controller;

import com.learning.report.service.ReportService;
import com.learning.common.dto.LearningResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<List<LearningResponse>>
           generateReportByEmployeeId(
           @PathVariable("id") Long id,
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   reportService
                       .generateReportByEmployeeId(
                        id, username),
                   HttpStatus.OK);
    }

    @GetMapping("/team")
    public ResponseEntity<List<LearningResponse>>
           generateReportByTeam(
           @RequestHeader("username")
           String username) {
        return new ResponseEntity<>(
                   reportService
                       .generateReportByTeam(
                        username),
                   HttpStatus.OK);
    }
}
