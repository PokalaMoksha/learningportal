package com.learning.learning.service;

import com.learning.common.dto.*;
import java.util.List;

public interface LearningService {

    // ── L&D APIs ──────────────────────────────

    LearningResponse updateLearningDetails(
                     Long employeeId,
                     LearningRequest request,
                     String username);

    // ── Feign APIs ────────────────────────────

    List<LearningResponse> getLearningByEmployee(
                           Long employeeId);

    List<LearningResponse> getLearningByManager(
                           Long managerId);

    List<LearningResponse> getLearningByCsm(
                           Long csmId);
}
