package com.learning.admin.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "manager-service")
public interface ManagerClient {

    @GetMapping("/manager/all")
    List<ManagerResponse> getAllManagers();

    @PostMapping("/manager/add")
    ManagerResponse addManager(
        @RequestBody UserRequest request);
}