package com.learning.auth.service;

import com.learning.auth.repository.UserRepository;
import com.learning.auth.security.JwtHelper;
import com.learning.common.dto.*;
import com.learning.common.entity.User;
import com.learning.common.enums.Role;
import com.learning.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl
       implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager
                  authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public LoginResponse login(
                       LoginRequest request) {

        log.info("Login attempt: {}",
                 request.getUsername());

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        User user = userRepository
                      .findByUsername(
                       request.getUsername())
                      .orElseThrow(() ->
                          new UserNotFoundException(
                          "User not found!"));

        String token = jwtHelper.generateToken(
                user.getUsername(),
                user.getRole().name());

        log.info("Login successful: {}",
                 request.getUsername());

        return LoginResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }

    @Override
    @Transactional
    public UserResponse createUser(
                        UserRequest request) {

        log.info("Creating user: {}",
                 request.getUsername());

        if (userRepository.existsByUsername(
                           request.getUsername()))
            throw new BadApiRequest(
                "Username already exists!");

        if (userRepository.existsByEmail(
                           request.getEmail()))
            throw new BadApiRequest(
                "Email already exists!");

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(
                          request.getPassword()))
                .email(request.getEmail())
                .role(Role.valueOf(
                      request.getRole()
                             .toUpperCase()))
                .isActive(true)
                .build();

        userRepository.save(user);

        log.info("User created: {}",
                 request.getUsername());

        return modelMapper.map(user,
               UserResponse.class);
    }

    @Override
    public UserResponse getUserByUsername(
                        String username) {

        log.info("Fetching user: {}", username);

        User user = userRepository
                      .findByUsername(username)
                      .orElseThrow(() ->
                          new UserNotFoundException(
                          "User not found!"));

        return modelMapper.map(user,
               UserResponse.class);
    }
    @Override
public UserResponse getUserById(Long id) {

    log.info("Fetching user by id: {}", id);

    User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException(
                    "User not found!"));

    return modelMapper.map(user,
           UserResponse.class);
}
}
