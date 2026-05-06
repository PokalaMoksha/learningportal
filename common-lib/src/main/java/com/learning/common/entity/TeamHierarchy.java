package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "team_hierarchy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamHierarchy {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long csmId;

    @Column(nullable = false)
    private Long managerId;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;
}
