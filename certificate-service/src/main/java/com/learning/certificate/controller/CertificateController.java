package com.learning.certificate.controller;

import com.learning.certificate.service
       .CertificateService;
import com.learning.common.annotation.Auditable;
import com.learning.common.annotation.CheckHierarchy;
import com.learning.common.dto.*;
import com.learning.common.enums.AuditAction;
import com.learning.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService
                  certificateService;

    // ── Employee APIs ─────────────────────────

    @CheckHierarchy
    @Auditable(action = AuditAction.UPLOAD_CERTIFICATE)
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseMessage>
           uploadCertificate(
           @RequestParam("platform") String platform,
           @RequestParam("certificateName") String certificateName,
           @RequestParam("employeeId") Long employeeId,
           @RequestParam("file") MultipartFile file,
           @RequestHeader("username")
           String username) throws IOException {

        CertificateRequest request =
            CertificateRequest.builder()
                .platform(platform)
                .certificateName(certificateName)
                .employeeId(employeeId)
                .file(file)
                .build();

        certificateService
            .uploadCertificate(request, username);

        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Certificate uploaded!")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return new ResponseEntity<>(
                   response,
                   HttpStatus.CREATED);
    }

    // ── Feign APIs ────────────────────────────

    @CheckHierarchy
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<CertificateResponse>>
           getCertificatesByEmployee(
           @PathVariable("employeeId") Long employeeId) {
        return new ResponseEntity<>(
                   certificateService
                       .getCertificatesByEmployee(
                        employeeId),
                   HttpStatus.OK);
    }

    @CheckHierarchy
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]>
           downloadCertificate(
           @PathVariable("id") Long id)
           throws IOException {

        InputStream inputStream =
            certificateService
                .downloadCertificate(id);

        return new ResponseEntity<>(
                   inputStream.readAllBytes(),
                   HttpStatus.OK);
    }
}