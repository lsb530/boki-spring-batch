package com.boki.batch.killbatchsystem.config;

import com.boki.batch.killbatchsystem.tasklet.DeleteOldFilesTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.TimeUnit;

@Configuration
public class FileCleanupBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public FileCleanupBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job deleteOldFilesJob() {
        return new JobBuilder("deleteOldFilesJob", jobRepository)
            .start(deleteOldFilesStep())
            .build();
    }

    @Bean
    public Step deleteOldFilesStep() {
        return new StepBuilder("deleteOldFilesStep", jobRepository)
            .tasklet(deleteOldFilesTasklet(), transactionManager)
            .build();
    }

    @Bean
    public Tasklet deleteOldFilesTasklet() {
        // "temp" 디렉토리에서 30일 이상 지난 파일 삭제
        return new DeleteOldFilesTasklet("/path/to/temp", 30, TimeUnit.DAYS); // OS
        // return new DeleteOldFilesTasklet("temp", 30, TimeUnit.MINUTES); // 현재 프로젝트
    }

}
