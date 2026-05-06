package com.learning.admin.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "csm-service")
public interface CsmClient {

    @GetMapping("/csm/all")
    List<CsmResponse> getAllCsms();

    @PostMapping("/csm/add")
    CsmResponse addCsm(
        @RequestBody UserRequest request);
}