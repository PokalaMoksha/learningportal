package com.learning.common.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAccessResponse {

    private Long id;
    private Long csmId;
    private Long targetCsmId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
}