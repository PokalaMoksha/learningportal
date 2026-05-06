package com.learning.employee.service;

import com.learning.common.dto.*;
import java.util.List;

public interface EmployeeService {

    // ── Employee APIs ──────────────────────────

    EmployeeResponse getMyDetails(
                     String username);

    List<LearningResponse> getMyLearning(
                           String username);

    ManagerResponse getMyManager(
                    String username);

    List<CertificateResponse> getMyCertificates(
                              String username);

    // ── Feign APIs ─────────────────────────────

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getEmployeesByManager(
                           Long managerId);

    boolean existsByIdAndManagerId(
            Long id, Long managerId);

    EmployeeResponse addEmployee(
                     UserRequest request);

    EmployeeResponse updateEmployee(
                     Long id,
                     UserRequest request);
}