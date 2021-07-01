package com.example.netty.config;

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
    private NettyServer nettyServer;

    @Override
    public void run(ApplicationArguments args) {
        //启动netty服务端
        nettyServer.start();
    }
}