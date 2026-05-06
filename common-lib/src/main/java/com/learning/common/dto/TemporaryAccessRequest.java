package com.learning.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAccessRequest {

    @NotNull(message = "CSM id is required")
    private Long csmId;

    @NotNull(
        message = "Target CSM id is required")
    private Long targetCsmId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;
}