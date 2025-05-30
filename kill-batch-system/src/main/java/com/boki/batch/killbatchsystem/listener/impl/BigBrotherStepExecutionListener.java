package com.boki.batch.killbatchsystem.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BigBrotherStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(@NonNull StepExecution stepExecution) {
        log.info("Step 구역 감시 시작. 모든 행동이 기록된다.");
    }

    @Override
    public ExitStatus afterStep(@NonNull StepExecution stepExecution) {
        log.info("Step 감시 종료. 모든 행동이 기록되었다.");
        log.info("Big Brother의 감시망 작동중..");
        return ExitStatus.COMPLETED;
    }

}
