package com.learning.common.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamHierarchyResponse {

    private Long id;
    private Long csmId;
    private Long managerId;
    private Long employeeId;
    private boolean isActive;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}
