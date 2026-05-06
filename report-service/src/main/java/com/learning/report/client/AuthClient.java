package com.learning.report.client;

import com.learning.common.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/auth/user/username/{username}")
    UserResponse getUserByUsername(
        @PathVariable("username") String username);
}
