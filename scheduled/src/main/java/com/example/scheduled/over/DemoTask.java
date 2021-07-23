package com.example.scheduled.over;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("demoTask")
@Slf4j
public class DemoTask {
    public void taskWithParams(String params) {
        log.info("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        log.info("执行无参示例任务");
    }
}  