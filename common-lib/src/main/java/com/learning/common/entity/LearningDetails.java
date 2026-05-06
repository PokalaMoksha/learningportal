package com.learning.common.entity;

import com.learning.common.enums.LearningLevel;
import com.learning.common.enums.LearningTrack;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningDetails {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LearningTrack learningTrack;

    @Column(nullable = false)
    private String technologies;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LearningLevel learningLevel;

    @Column(nullable = false)
    private Integer modulesCompleted = 0;

    @Column(nullable = false)
    private Integer examAttempts = 0;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private Long updatedByUserId;
}