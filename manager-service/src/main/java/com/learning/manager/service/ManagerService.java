package com.learning.manager.service;

import com.learning.common.dto.*;
import java.util.List;

public interface ManagerService {

    // ── Manager APIs ───────────────────────────

    List<EmployeeResponse> getMyTeam(
                           String username);

    List<LearningResponse> getEmployeeLearning(
                           Long employeeId,
                           String username);

    List<CertificateResponse>
           getEmployeeCertificates(
           Long employeeId,
           String username);

    EmployeeResponse addEmployee(
                     UserRequest request,
                     String username);

    EmployeeResponse updateEmployee(
                     Long id,
                     UserRequest request,
                     String username);

    List<LearningResponse>
           generateReportByEmployeeId(
           Long employeeId,
           String username);

    List<LearningResponse> generateReportByTeam(
                           String username);

    // ── Feign APIs ─────────────────────────────

    List<ManagerResponse> getAllManagers();

    ManagerResponse getManagerById(Long id);

    List<ManagerResponse> getManagersByCsm(
                          Long csmId);

    boolean existsByIdAndCsmId(
            Long id, Long csmId);

    ManagerResponse addManager(
                    UserRequest request);

    ManagerResponse updateManager(
                    Long id,
                    UserRequest request);
}