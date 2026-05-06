package com.learning.csm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.csm"
    }
)
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.csm"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.csm.repository"
})
public class CsmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            CsmServiceApplication.class,
            args);
    }
}
