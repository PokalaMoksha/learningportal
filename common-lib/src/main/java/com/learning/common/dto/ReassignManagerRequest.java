package com.learning.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReassignManagerRequest {

    @NotNull(message = "Manager id is required")
    private Long managerId;

    @NotNull(message = "New CSM id is required")
    private Long newCsmId;
}
