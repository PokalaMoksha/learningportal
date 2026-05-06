package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "managers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false, unique = true)
    private String managerCode;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long csmId;
}