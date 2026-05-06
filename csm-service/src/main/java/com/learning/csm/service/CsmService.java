package com.learning.csm.service;

import com.learning.common.dto.*;
import java.util.List;

public interface CsmService {

    // ── CSM APIs ───────────────────────────────

    List<ManagerResponse> getMyManagers(
                          String username);

    List<EmployeeResponse> getMyTeams(
                           String username);

    List<LearningResponse> getEmployeeLearning(
                           Long employeeId,
                           String username);

    List<CertificateResponse>
           getEmployeeCertificates(
           Long employeeId,
           String username);

    ManagerResponse addManager(
                    UserRequest request,
                    String username);

    EmployeeResponse addEmployee(
                     UserRequest request,
                     String username);

    ManagerResponse updateManager(
                    Long managerId,
                    UserRequest request,
                    String username);

    EmployeeResponse updateEmployee(
                     Long employeeId,
                     UserRequest request,
                     String username);

    void reassignEmployee(
         ReassignEmployeeRequest request,
         String username);

    void reassignManager(
         ReassignManagerRequest request,
         String username);

    // ── Report APIs ────────────────────────────

    List<LearningResponse>
           generateReportByEmployeeId(
           Long employeeId,
           String username);

    List<LearningResponse> generateReportByTeam(
                           String username);

    // ── Feign APIs ─────────────────────────────

    List<CsmResponse> getAllCsms();

    CsmResponse getCsmById(Long id);

    CsmResponse addCsm(UserRequest request);

    TeamHierarchyResponse
           getHierarchyByEmployee(
           Long employeeId);
}