package com.learning.report.client;

import com.learning.common.dto.ManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "manager-service")
public interface ManagerClient {

    @GetMapping("/manager/{id}")
    ManagerResponse getManagerById(
        @PathVariable("id") Long id);

    @GetMapping("/manager/csm/{csmId}")
    List<ManagerResponse> getManagersByCsm(
        @PathVariable("csmId") Long csmId);

    @GetMapping("/manager/exists/{id}/csm/{csmId}")
    boolean existsByIdAndCsmId(
        @PathVariable("id") Long id,
        @PathVariable("csmId") Long csmId);
}
