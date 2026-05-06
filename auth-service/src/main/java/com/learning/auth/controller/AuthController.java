package com.learning.auth.controller;

import com.learning.auth.service.AuthService;
import com.learning.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>
           login(
           @RequestBody
           LoginRequest request) {
        return new ResponseEntity<>(
                   authService.login(request),
                   HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse>
           createUser(
           @RequestBody
           UserRequest request) {
        return new ResponseEntity<>(
                   authService
                       .createUser(request),
                   HttpStatus.CREATED);
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<UserResponse>
           getUserByUsername(
           @PathVariable("username")
           String username) {
        return new ResponseEntity<>(
                   authService
                       .getUserByUsername(
                        username),
                   HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse>
           getUserById(
           @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                   authService
                       .getUserById(id),
                   HttpStatus.OK);
    }
}