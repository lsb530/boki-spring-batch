package com.boki.batch.killbatchsystem.jobparameter.pojo;

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
public class PojoTerminationBatchConfig {

    // --spring.batch.job.name=pojoProcessTerminatorJob missionName=안산_데이터센터_침투,java.lang.String operationCommander=KILL-9 securityLevel=3,java.lang.Integer,false

    @Bean
    public Job pojoProcessTerminatorJob(
        JobRepository jobRepository,
        Step pojoTerminationStep
    ) {
        return new JobBuilder("pojoProcessTerminatorJob", jobRepository)
            .start(pojoTerminationStep)
            .build();
    }

    @Bean
    public Step pojoTerminationStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        Tasklet pojoTerminateTasklet
    ) {
        return new StepBuilder("pojoTerminationStep", jobRepository)
            .tasklet(pojoTerminateTasklet, transactionManager)
            .build();
    }

}
