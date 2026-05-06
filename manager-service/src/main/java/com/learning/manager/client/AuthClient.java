package com.learning.manager.client;

import com.learning.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/auth/create-user")
    UserResponse createUser(
        @RequestBody UserRequest request);

    @GetMapping("/auth/user/username/{username}")
    UserResponse getUserByUsername(
        @PathVariable("username")
        String username);

    @GetMapping("/auth/user/{id}")
    UserResponse getUserById(
        @PathVariable("id") Long id);
}