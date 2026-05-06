package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "temporary_access")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryAccess {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private Long csmId;

    @Column(nullable = false)
    private Long targetCsmId;

    @Column(nullable = false)
    private Long grantedByUserId;
}