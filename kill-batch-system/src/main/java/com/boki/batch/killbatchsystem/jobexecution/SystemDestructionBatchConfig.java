package com.boki.batch.killbatchsystem.jobexecution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SystemDestructionBatchConfig {

    // --spring.batch.job.name=systemDestructionJob destructionPower=10,java.lang.Long

    @Bean
    public Job systemDestructionJob1(
        JobRepository jobRepository,
        Step destructionStep,
        SystemDestructionValidator validator
    ) {
        // 직접 구현한 validator 사용
        return new JobBuilder("systemDestructionJob", jobRepository)
            .validator(validator)
            .start(destructionStep)
            .build();
    }

    // @Bean
    public Job systemDestructionJob2(
        JobRepository jobRepository,
        Step destructionStep,
        SystemDestructionValidator validator
    ) {
        // 단순히 파라미터의 존재여부만 확인하는 방법
        return new JobBuilder("systemDestructionJob", jobRepository)
            .validator(new DefaultJobParametersValidator(
                new String[]{"destructionPower"},  // 필수 파라미터
                new String[]{"targetSystem"}       // 선택적 파라미터
            ))
            .start(destructionStep)
            .build();
    }


    // @Bean
    public Job systemDestructionJob3(
        JobRepository jobRepository,
        Step destructionStep,
        SystemDestructionValidator validator
    ) {
        // 필수 파라미터의 존재 여부만 검증하고, 다른 파라미터들은 자유롭게 전달하고 싶을 때
        return new JobBuilder("systemDestructionJob", jobRepository)
            .validator(new DefaultJobParametersValidator(
                new String[]{"destructionPower"},  // 필수 파라미터
                new String[]{}                     // 선택적 파라미터는 빈 배열로
            ))
            .start(destructionStep)
            .build();
    }

}
