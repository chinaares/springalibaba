package com.example.redis.controller;

import com.example.redis.impl.RedisTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhao
 * @desc ...
 * @date 2020-12-04 15:40:18
 */
@RestController
public class RedisTestController {
    /*SpringBoot默认的AOP实现就是使用的CGLib代理*/
    @Autowired
    private RedisTestServiceImpl redisTestService;

    @GetMapping("/vote")
    public void vote() {
         redisTestService.vote();
    }
    @GetMapping("/publish")
    public void publish() {
         redisTestService.publish();
    }

    /**
     * 排序
     */
    @GetMapping("/sort")
    public void sort() {
         redisTestService.sort();
    }

    /**
     * 管道批量操作
     */
    @GetMapping("/pipelineTest")
    public void pipelineTest() {
         redisTestService.pipelineTest();
    }

    /**
     * 事务测试1
     */
    @GetMapping("/transactionalTest")
    public void transactionalTest() {
         redisTestService.transactionalTest();
    }

    /**
     * 事务测试2
     */
    @GetMapping("/transactionalTest2")
    public void transactionalTest2() {
        redisTestService.transactionalTest2();
    }

    /**
     * 锁验证
     */
    @GetMapping("/lockTest")
    public void lockTest() {
        redisTestService.lockTest();
    }


}
