package com.learning.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
    scanBasePackages = {
        "com.learning.common",
        "com.learning.report"
    }
)
@EnableDiscoveryClient
@EnableFeignClients(
    basePackages="com.learning.report.client"
)
public class ReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            ReportServiceApplication.class,
            args);
    }
}