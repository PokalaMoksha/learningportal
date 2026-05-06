package com.learning.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.audit"
    }
)
@EnableDiscoveryClient
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.audit"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.audit.repository"
})
public class AuditServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            AuditServiceApplication.class,
            args);
    }
}