package com.boki.batch.killbatchsystem.jobexecution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component // or @Configuration
public class SystemDestructionStep {

    @Bean("destructionStep")
    @JobScope
    public Step systemDestructionStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        @Value("#{jobParameters['destructionPower']}") Long destructionPower
    ) {
        return new StepBuilder("systemDestructionStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("시스템 파괴 프로세스가 시작되었습니다. 파괴력: {}", destructionPower);
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

}
