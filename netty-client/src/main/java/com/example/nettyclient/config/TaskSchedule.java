package com.example.nettyclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * 用于Spring加载完成后自动执行一些任务。
 */
@Slf4j
@Service
public class TaskSchedule implements ApplicationRunner{

    @Autowired
    private NettyClient nettyClient;

    @Override
    public void run(ApplicationArguments args) {
        //启动netty客户端
        nettyClient.start();
    }
}