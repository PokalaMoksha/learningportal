package com.learning.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.manager"
    }
)
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.manager"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.manager.repository"
})
public class ManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            ManagerServiceApplication.class,
            args);
    }
}