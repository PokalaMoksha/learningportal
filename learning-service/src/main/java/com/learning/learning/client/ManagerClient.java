package com.learning.learning.client;

import com.learning.common.dto.ManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "manager-service")
public interface ManagerClient {

    @GetMapping("/manager/csm/{csmId}")
    List<ManagerResponse> getManagersByCsm(
        @PathVariable("csmId") Long csmId);
}
