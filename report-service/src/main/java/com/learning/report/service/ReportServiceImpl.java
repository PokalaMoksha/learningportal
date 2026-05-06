package com.learning.report.service;

import com.learning.report.client.*;
import com.learning.common.dto.*;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl
       implements ReportService {

    private final AuthClient authClient;
    private final LearningClient learningClient;

    @Override
    public List<LearningResponse>
           generateReportByEmployeeId(
           Long employeeId,
           String username) {

        log.info("Generating report " +
                 "for employee: {}", employeeId);

        // AOP handles validation! ✅

        return learningClient
                .getLearningByEmployee(employeeId);
    }

    @Override
    public List<LearningResponse>
           generateReportByTeam(
           String username) {

        log.info("Generating team report for: {}",
                 username);

        // AOP handles role check! ✅

        UserResponse user =
            authClient.getUserByUsername(username);

        if (user == null)
            throw new UserNotFoundException(
                "User not found!");

        return learningClient
                .getLearningByEmployee(
                 user.getId());
    }
}