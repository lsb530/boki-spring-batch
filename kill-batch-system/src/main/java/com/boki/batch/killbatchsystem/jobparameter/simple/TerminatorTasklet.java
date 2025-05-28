package com.boki.batch.killbatchsystem.jobparameter.simple;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class TerminatorTasklet implements Tasklet {

    // --spring.batch.job.name=processTerminatorJob terminatorId=KILL-9,java.lang.String targetCount=5,java.lang.Integer

    @Value("#{jobParameters['terminatorId']}")
    String terminatorId;

    @Value("#{jobParameters['targetCount']}")
    Integer targetCount;

    @Override
    public RepeatStatus execute(
        @Nonnull StepContribution contribution,
        @Nonnull ChunkContext chunkContext
    ) throws Exception {
        log.info("시스템 종결자 정보:");
        log.info("ID: {}", terminatorId);
        log.info("제거 대상 수: {}", targetCount);
        log.info("⚡ SYSTEM TERMINATOR {} 작전을 개시합니다.", terminatorId);
        log.info("☠️ {}개의 프로세스를 종료합니다.", targetCount);

        for (int i = 1; i <= targetCount; i++) {
            log.info("💀 프로세스 {} 종료 완료!", i);
        }

        log.info("🎯 임무 완료: 모든 대상 프로세스가 종료되었습니다.");
        return RepeatStatus.FINISHED;
    }

}
