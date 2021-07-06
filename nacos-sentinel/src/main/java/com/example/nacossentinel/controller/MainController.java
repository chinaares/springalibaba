package com.example.nacossentinel.controller;

import com.example.nacossentinel.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("api")
@RestController
@Api(tags = "nacos-sentinel")
@Slf4j
public class MainController {

    @Autowired
    private TestService testService;

    @GetMapping("/consumer")
    @ApiOperation(value = "你好", notes = "consumer")
    public String consumer() {
        return testService.consumer();
    }

    @GetMapping("/doSomeThing")
    public String doSomeThing() {
        testService.doSomeThing("hello " + new Date());
        return "doSomeThing";
    }

    @GetMapping("/doSomeThing2")
    public String doSomeThing2() {
        testService.doSomeThing2("hello2 " + new Date());
        return "doSomeThing2";
    }

}