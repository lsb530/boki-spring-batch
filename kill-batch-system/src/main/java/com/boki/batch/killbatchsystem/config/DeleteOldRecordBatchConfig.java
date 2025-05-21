package com.boki.batch.killbatchsystem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 매일 밤 7일이 지난 레코드를 created_at 컬럼 기준으로 삭제하는 작업
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeleteOldRecordBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Bean
    public Job deleteOldRecordsJob() {
        return new JobBuilder("deleteOldRecordsJob", jobRepository)
            .start(deleteOldRecordsStep())
            .next(logRemainingRecordsStep())
            .build();
    }

    @Bean
    public Step deleteOldRecordsStep() {
        return new StepBuilder("deleteOldRecordsStep", jobRepository)
            .tasklet(this::deleteOldRecordsTasklet, transactionManager)
            .build();
    }

    // private RepeatStatus deleteOldRecordsTasklet(StepContribution contribution,
    //     ChunkContext chunkContext) throws Exception {
    //
    //     String sql;
    //     try (Connection conn = dataSource.getConnection()) {
    //         String url = conn.getMetaData().getURL();
    //
    //         Instant cutoff = Instant.now().minus(7, ChronoUnit.DAYS);
    //         Timestamp ts = Timestamp.from(cutoff);
    //
    //         if (url.startsWith("jdbc:h2:")) { // 7일
    //             // sql = "DELETE FROM logs WHERE created_at < dateadd('DAY', -7, NOW())";
    //             sql = "DELETE FROM logs WHERE created_at < dateadd('SECOND', -604800, NOW())";
    //         }
    //         else {
    //             sql = "DELETE FROM logs WHERE created_at < NOW() - INTERVAL 7 DAY";
    //         }
    //     }
    //     int deleted = jdbcTemplate.update(sql);
    //     log.info("🗑️ {}개의 오래된 레코드가 삭제되었습니다.", deleted);
    //     return RepeatStatus.FINISHED;
    // }

    private RepeatStatus deleteOldRecordsTasklet(StepContribution contribution,
        ChunkContext chunkContext) throws Exception {
        // 7일 전 시각 계산
        Instant cutoffInstant = Instant.now().minus(7, ChronoUnit.DAYS);
        Timestamp cutoffTs = Timestamp.from(cutoffInstant);

        // Parameter Binding 형태의 SQL (DB 독립적)
        String sql = "DELETE FROM logs WHERE created_at < ?";

        // JdbcTemplate 에서 cutoffTs 바인딩
        int deleted = jdbcTemplate.update(sql, cutoffTs);

        log.info("🗑️ {}개의 오래된 레코드가 삭제되었습니다.", deleted);
        return RepeatStatus.FINISHED;
    }

    @Bean
    public Step logRemainingRecordsStep() {
        return new StepBuilder("logRemainingRecordsStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                List<String> messages = jdbcTemplate.query(
                    "SELECT message FROM logs",
                    (rs, rowNum) -> rs.getString("message")
                );
                log.info("📋 현재 logs 테이블에 {}개의 레코드가 남았습니다.", messages.size());
                for (String msg : messages) {
                    log.info("   - {}", msg);
                }
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

}
