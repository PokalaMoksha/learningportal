package com.learning.notification.batch;

import com.learning.common.entity.NotificationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder
       .JobBuilder;
import org.springframework.batch.core.repository
       .JobRepository;
import org.springframework.batch.core.step.builder
       .StepBuilder;
import org.springframework.context.annotation
       .Bean;
import org.springframework.context.annotation
       .Configuration;
import org.springframework.transaction
       .PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class NotificationJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager
                  transactionManager;
    private final NotificationItemReader reader;
    private final NotificationItemProcessor
                  processor;
    private final NotificationItemWriter writer;

    @Bean
    public Job notificationJob() {
        return new JobBuilder(
                   "notificationJob",
                   jobRepository)
                .start(notificationStep())
                .build();
    }

    @Bean
    public Step notificationStep() {
        return new StepBuilder(
                   "notificationStep",
                   jobRepository)
                .<NotificationLog,
                  NotificationLog>chunk(
                  10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
