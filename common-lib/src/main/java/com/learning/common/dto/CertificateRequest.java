package com.learning.common.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequest {

    @NotBlank(message = "Platform is required")
    private String platform;

    @NotBlank(
        message = "Certificate name is required")
    private String certificateName;

    @NotNull(message = "Employee id is required")
    private Long employeeId;

    @NotNull(message = "File is required")
    private MultipartFile file;
}
