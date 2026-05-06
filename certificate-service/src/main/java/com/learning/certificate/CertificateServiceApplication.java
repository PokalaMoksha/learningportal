package com.learning.certificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.certificate"
    }
)
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = {
    "com.learning.common.entity",
    "com.learning.certificate"
})
@EnableJpaRepositories(basePackages = {
    "com.learning.certificate.repository"
})
public class CertificateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            CertificateServiceApplication.class,
            args);
    }
}