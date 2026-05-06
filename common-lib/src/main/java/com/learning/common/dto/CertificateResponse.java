package com.learning.common.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponse {

    private Long id;
    private String platform;
    private String certificateName;
    private String filePath;
    private LocalDate uploadDate;
    private Long employeeId;
}