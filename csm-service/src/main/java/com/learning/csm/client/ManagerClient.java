package com.learning.csm.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "manager-service")
public interface ManagerClient {

    @GetMapping("/manager/csm/{csmId}")
    List<ManagerResponse> getManagersByCsm(
        @PathVariable("csmId") Long csmId);

    @GetMapping("/manager/{id}")
    ManagerResponse getManagerById(
        @PathVariable("id") Long id);

    @PostMapping("/manager/add")
    ManagerResponse addManager( // ← Add this! ✅
        @RequestBody UserRequest request);

    @PutMapping("/manager/update/{id}")
    ManagerResponse updateManager(
        @PathVariable("id") Long id,
        @RequestBody UserRequest request);

    @GetMapping("/manager/exists/{id}/csm/{csmId}")
    boolean existsByIdAndCsmId(
        @PathVariable("id") Long id,
        @PathVariable("csmId") Long csmId);
}