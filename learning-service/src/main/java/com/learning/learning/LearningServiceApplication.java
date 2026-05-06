package com.learning.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.learning"
    }
)
@EnableDiscoveryClient
@EnableFeignClients(
    basePackages = "com.learning.learning.client"
)
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.learning"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.learning.repository"
})
public class LearningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            LearningServiceApplication.class,
            args);
    }
}