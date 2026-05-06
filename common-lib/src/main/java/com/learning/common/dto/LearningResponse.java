package com.learning.common.dto;

import com.learning.common.enums.LearningLevel;
import com.learning.common.enums.LearningTrack;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningResponse {

    private Long id;
    private LearningTrack learningTrack;
    private String technologies;
    private LearningLevel learningLevel;
    private Integer modulesCompleted;
    private Integer examAttempts;
    private LocalDateTime lastUpdated;
    private Long employeeId;
}
