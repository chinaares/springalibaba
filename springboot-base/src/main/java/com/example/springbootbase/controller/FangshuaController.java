package com.example.springbootbase.controller;


import com.example.common.exception.basic.APIResponse;
import com.example.springbootbase.config.AccessLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class FangshuaController {

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping("/fangshua")
    @ResponseBody
    public APIResponse<String> fangshua() {
        return APIResponse.ok("请求成功");

    }
}