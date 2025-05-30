package com.boki.batch.killbatchsystem.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class BigBrotherBatchConfig {

    // --spring.batch.job.name=systemMonitoringJob

    @Bean
    public Job systemMonitoringJob(
        JobRepository jobRepository,
        Step systemMonitoringStep,
        BigBrotherJobExecutionListener jobListener
    ) {
        return new JobBuilder("systemMonitoringJob", jobRepository)
            .listener(jobListener)
            .start(systemMonitoringStep)
            .build();
    }

    @Bean
    public Step systemMonitoringStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        BigBrotherStepExecutionListener stepListener
    ) {
        return new StepBuilder("systemMonitoringStep", jobRepository)
            .listener(stepListener)
            .tasklet((contribution, chunkContext) -> {
                log.info("systemMonitoringStep 시작");
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

}
