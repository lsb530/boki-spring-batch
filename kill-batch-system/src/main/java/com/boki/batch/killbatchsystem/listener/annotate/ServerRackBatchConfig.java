package com.boki.batch.killbatchsystem.listener.annotate;

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
public class ServerRackBatchConfig {

    // --spring.batch.job.name=serverRackControlJob

    @Bean
    public Job serverRackControlJob(
        JobRepository jobRepository,
        Step serverRackControlStep
    ) {
        return new JobBuilder("serverRackControlJob", jobRepository)
            .listener(new ServerRoomInfiltrationListener())
            .start(serverRackControlStep)
            .build();
    }

    @Bean
    public Step serverRackControlStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("serverRackControlStep", jobRepository)
            .listener(new ServerRackControlListener())
            .tasklet((contribution, chunkContext) -> {
                log.info("serverRackControlStep 시작");
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

}
