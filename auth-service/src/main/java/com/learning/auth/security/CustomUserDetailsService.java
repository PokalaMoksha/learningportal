package com.learning.auth.security;

import com.learning.auth.repository.UserRepository;
import com.learning.common.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
       implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
                        String username)
                        throws UsernameNotFoundException {

        log.info("Loading user: {}", username);

        User user = userRepository
                      .findByUsername(username)
                      .orElseThrow(() ->
                          new UsernameNotFoundException(
                          "User not found: "
                          + username));

        return org.springframework.security
                  .core.userdetails.User
                  .builder()
                  .username(user.getUsername())
                  .password(user.getPassword())
                  .authorities(
                   Collections.singletonList(
                   new SimpleGrantedAuthority(
                   "ROLE_" +
                   user.getRole().name())))
                  .build();
    }
}
