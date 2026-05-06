package com.learning.report.service;

import com.learning.common.dto.LearningResponse;
import java.util.List;

public interface ReportService {

    List<LearningResponse> generateReportByEmployeeId(
                           Long employeeId,
                           String username);

    List<LearningResponse> generateReportByTeam(
                           String username);
}
