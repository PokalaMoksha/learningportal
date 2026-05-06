package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "csm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Csm {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String csmName;

    @Column(nullable = false, unique = true)
    private String csmCode;

    @Column(nullable = false)
    private Long userId;
}