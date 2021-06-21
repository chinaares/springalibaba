package com.example.nacosfeign.fallback;


import com.example.nacosfeign.remote.AuthFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AuthFallbackFactory implements FallbackFactory<AuthFeignClient> {
    @Override
    public AuthFeignClient create(Throwable throwable) {
        return new AuthFeignClient() {
            @Override
            public String helloNacos() {
                return "请求超时了";
            }
        };
    }
}
