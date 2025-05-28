package com.boki.batch.killbatchsystem.jobparameter.time;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class TimeTerminatorBatchConfig {

    @Bean
    public Job timeTerminatorJob(JobRepository jobRepository, Step timeTerminationStep) {
        return new JobBuilder("timeTerminatorJob", jobRepository)
            .start(timeTerminationStep)
            .build();
    }

    @Bean
    public Step timeTerminationStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        Tasklet timeTerminatorTasklet
    ) {
        return new StepBuilder("timeTerminationStep", jobRepository)
            .tasklet(timeTerminatorTasklet, transactionManager)
            .build();
    }

}
