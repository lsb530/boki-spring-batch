package com.boki.batch.killbatchsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlarmService {

    public void send(String message) {
        System.out.println(message);
        log.info("알람 발송!!");
    }

}
