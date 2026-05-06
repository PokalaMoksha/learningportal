package com.learning.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReassignEmployeeRequest {

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "New manager id is required")
    private Long newManagerId;
}
