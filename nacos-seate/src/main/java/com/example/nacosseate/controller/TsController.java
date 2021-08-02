package com.example.nacosseate.controller;

import com.example.common.exception.basic.APIResponse;
import com.example.nacosseate.service.TsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-08-02 14:02:58
 */
@RestController
@RequestMapping("/TS")
public class TsController {

    @Autowired
    private TsService tsService;
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/testTransactional")
    public APIResponse testTransactional() {
        tsService.testTransactional();
        return APIResponse.ok();
    }

}
