package com.learning.learning.controller;

import com.learning.learning.service.LearningService;
import com.learning.common.annotation.Auditable;
import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.dto.*;
import com.learning.common.enums.AuditAction;
import com.learning.common.response.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/learning")
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;

    // ── L&D APIs ──────────────────────────────

    @CheckHierarchy
    @Auditable(action = AuditAction.UPDATE_LEARNING)
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<ApiResponseMessage>
           updateLearningDetails(
           @PathVariable("employeeId") Long employeeId,
           @Valid @RequestBody
           LearningRequest request,
           @RequestHeader("username")
           String username) {

        learningService.updateLearningDetails(
            employeeId, request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Learning updated!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.OK);
    }

    // ── Feign APIs ────────────────────────────

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LearningResponse>>
           getLearningByEmployee(
           @PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(
                   learningService
                       .getLearningByEmployee(
                        employeeId),
                   HttpStatus.OK);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<LearningResponse>>
           getLearningByManager(
           @PathVariable("managerId") Long managerId) {
        return new ResponseEntity<>(
                   learningService
                       .getLearningByManager(
                        managerId),
                   HttpStatus.OK);
    }

    @GetMapping("/csm/{csmId}")
    public ResponseEntity<List<LearningResponse>>
           getLearningByCsm(
           @PathVariable("csmId") Long csmId) {
        return new ResponseEntity<>(
                   learningService
                       .getLearningByCsm(csmId),
                   HttpStatus.OK);
    }
}