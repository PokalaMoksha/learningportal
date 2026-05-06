package com.learning.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "learning_certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningCertificate {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false)
    private String certificateName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private LocalDate uploadDate;

    @Column(nullable = false)
    private Long employeeId;
}
