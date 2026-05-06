package com.learning.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.admin"
    }
)
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.admin"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.admin.repository"
})
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            AdminServiceApplication.class,
            args);
    }
}