package com.learning.auth.service;

import com.learning.common.dto.*;

public interface AuthService {

    LoginResponse login(
        LoginRequest request);

    UserResponse createUser(
        UserRequest request);

    UserResponse getUserByUsername(
        String username);

    UserResponse getUserById(Long id);
}
