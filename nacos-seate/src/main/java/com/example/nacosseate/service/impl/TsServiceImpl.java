package com.example.nacosseate.service.impl;

import com.example.nacosseate.remote.AuthFeignClient;
import com.example.nacosseate.service.TsService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-08-02 14:05:14
 */
@Service
public class TsServiceImpl implements TsService {

    @Autowired
    private AuthFeignClient authFeignClient;

    @Override
    @GlobalTransactional
    public void testTransactional() {
        // 远程调用其他微服务的方法
        this.authFeignClient.helloNacos();
    }
}
