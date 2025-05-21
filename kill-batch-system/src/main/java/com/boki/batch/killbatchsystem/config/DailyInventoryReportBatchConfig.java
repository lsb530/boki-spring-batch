package com.boki.batch.killbatchsystem.config;

import com.boki.batch.killbatchsystem.repository.InventoryRepository;
import com.boki.batch.killbatchsystem.service.AlarmService;
import com.boki.batch.killbatchsystem.tasklet.DailyInventoryReportTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DailyInventoryReportBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final AlarmService alarmService;
    private final InventoryRepository inventoryRepository;

    @Bean
    public Job dailyInventoryReportJob() {
        return new JobBuilder("dailyInventoryReportJob", jobRepository)
            .start(dailyInventoryReportStep())
            .build();
    }

    @Bean
    public Step dailyInventoryReportStep() {
        return new StepBuilder("dailyInventoryReportStep", jobRepository)
            .tasklet(new DailyInventoryReportTasklet(alarmService, inventoryRepository), transactionManager)
            .build();
    }

}
