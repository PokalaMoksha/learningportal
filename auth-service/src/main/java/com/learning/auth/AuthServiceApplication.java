package com.learning.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.auth"
    }
)
@EnableDiscoveryClient
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.auth"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.auth.repository"
})
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            AuthServiceApplication.class,
            args);
    }
}