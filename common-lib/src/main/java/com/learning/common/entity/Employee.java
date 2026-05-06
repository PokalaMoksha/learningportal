package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false, unique = true)
    private String employeeCode;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long managerId;
}