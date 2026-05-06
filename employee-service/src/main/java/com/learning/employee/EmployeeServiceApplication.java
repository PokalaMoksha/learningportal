package com.learning.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.employee"
    }
)
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.employee"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.employee.repository"
})
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            EmployeeServiceApplication.class,
            args);
    }
}