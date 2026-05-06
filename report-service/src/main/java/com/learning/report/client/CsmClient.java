package com.learning.report.client;

import com.learning.common.dto.CsmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "csm-service")
public interface CsmClient {

    @GetMapping("/csm/{id}")
    CsmResponse getCsmById(
        @PathVariable("id") Long id);
}
