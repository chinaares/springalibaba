package com.example.nacosprovide.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
@Api(tags = "nacos-provide")
public class MainController {


    @GetMapping("/helloNacos")
    @ApiOperation(value = "你好", notes = "helloNacos")
    public String helloNacos(){
        return "你好，nacos！";
    }

}
