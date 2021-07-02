package com.example.nacosprovide.controller;

import com.example.common.exception.basic.APIResponse;
import com.example.common.exception.basic.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api")
@RestController
@Api(tags = "nacos-provide")
@Slf4j
public class MainController {


    @GetMapping("/helloNacos")
    @ApiOperation(value = "你好", notes = "helloNacos")
    public String helloNacos(){
        return "你好，nacos！";
    }

    @RequestMapping(value = "/parseDetailsFile", method = RequestMethod.POST)
    @ApiOperation(value = "详情文件解析",notes = "详情文件解析")
    public APIResponse parseDetailsFile(@RequestParam("file") MultipartFile file){
        try {
            return APIResponse.ok("解析成功");
        } catch (Exception e) {
            log.error("Error when parseDetailsFile, " + ExceptionUtils.getFullStackTrace(e));
            return APIResponse.fail(ResponseCode.FAIL);
        }
    }

}
