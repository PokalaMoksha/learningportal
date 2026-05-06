package com.learning.employee.client;

import com.learning.common.dto.CertificateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "certificate-service")
public interface CertificateClient {

    @GetMapping("/certificate/employee/{employeeId}")
    List<CertificateResponse> getCertificatesByEmployee(
        @PathVariable("employeeId") Long employeeId);
}
