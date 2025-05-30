package com.boki.batch.killbatchsystem.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BigBrotherJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NonNull JobExecution jobExecution) {
        log.info("Job 감시 시작. 모든 작업 감시 준비");
    }

    @Override
    public void afterJob(@NonNull JobExecution jobExecution) {
        log.info("Job 종료. 할당된 자원 정리 완료");
        log.info("시스템 상태: {}", jobExecution.getStatus());
    }

}
