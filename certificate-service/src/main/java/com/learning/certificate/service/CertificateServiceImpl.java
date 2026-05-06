package com.learning.certificate.service;

import com.learning.certificate.client.EmployeeClient;
import com.learning.certificate.repository
       .LearningCertificateRepository;
import com.learning.common.dto.*;
import com.learning.common.entity.*;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation
       .Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation
       .Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl
       implements CertificateService {

    private final LearningCertificateRepository
                  certificateRepository;
    private final FileService fileService;
    private final EmployeeClient employeeClient;
    private final ModelMapper modelMapper;

    @Value("${file.upload.path}")
    private String uploadPath;

    // ── Employee APIs ─────────────────────────

    @Override
    @Transactional
    public CertificateResponse uploadCertificate(
                               CertificateRequest
                               request,
                               String username)
                               throws IOException {

        log.info("Uploading certificate " +
                 "for employee: {}",
                 request.getEmployeeId());

        EmployeeResponse employee =
            employeeClient.getEmployeeById(
                request.getEmployeeId());

        if (employee == null)
            throw new EmployeeNotFoundException(
                "Employee not found!");

        String fileName = fileService
                            .uploadFile(
                             request.getFile(),
                             uploadPath);

        LearningCertificate certificate =
            LearningCertificate.builder()
                .platform(request.getPlatform())
                .certificateName(
                 request.getCertificateName())
                .filePath(fileName)
                .uploadDate(LocalDate.now())
                .employeeId(
                 request.getEmployeeId())
                .build();

        certificateRepository.save(certificate);

        log.info("Certificate uploaded!");

        return modelMapper.map(
               certificate,
               CertificateResponse.class);
    }

    // ── Feign APIs ────────────────────────────

    @Override
    public List<CertificateResponse>
           getCertificatesByEmployee(
           Long employeeId) {

        log.info("Fetching certificates " +
                 "for employee: {}", employeeId);

        return certificateRepository
                .findByEmployeeId(employeeId)
                .stream()
                .map(cert -> modelMapper
                             .map(cert,
                             CertificateResponse
                             .class))
                .collect(Collectors.toList());
    }

    @Override
    public InputStream downloadCertificate(
                       Long certificateId)
                       throws IOException {

        log.info("Downloading certificate: {}",
                 certificateId);

        LearningCertificate certificate =
            certificateRepository
                .findById(certificateId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                    "Certificate not found!"));

        return fileService.getResource(
               uploadPath,
               certificate.getFilePath());
    }
}
