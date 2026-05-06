package com.learning.notification.client;

import com.learning.common.dto.CsmResponse;
import com.learning.common.dto.TeamHierarchyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "csm-service")
public interface CsmClient {

    @GetMapping("/csm/{id}")
    CsmResponse getCsmById(
        @PathVariable("id") Long id);

    @GetMapping("/csm/hierarchy/employee/{employeeId}")
    TeamHierarchyResponse getHierarchyByEmployee(
        @PathVariable("employeeId")
        Long employeeId);
}
