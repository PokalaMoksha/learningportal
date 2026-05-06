package com.learning.common.dto;

import com.learning.common.enums.LearningLevel;
import com.learning.common.enums.LearningTrack;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningRequest {

    @NotNull(message = "Learning track required")
    private LearningTrack learningTrack;

    @NotBlank(message = "Technologies required")
    private String technologies;

    @NotNull(message = "Learning level required")
    private LearningLevel learningLevel;

    @NotNull(message = "Modules completed required")
    @Min(value = 0,
         message = "Cannot be negative")
    private Integer modulesCompleted;

    @NotNull(message = "Exam attempts required")
    @Min(value = 0,
         message = "Cannot be negative")
    private Integer examAttempts;
}
