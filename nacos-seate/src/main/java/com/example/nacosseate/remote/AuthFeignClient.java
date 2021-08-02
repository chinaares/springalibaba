package com.example.nacosseate.remote;


import com.example.nacosseate.fallback.AuthFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "nocas-provide", fallbackFactory = AuthFallbackFactory.class)
public interface AuthFeignClient {

    @GetMapping("/api/helloNacos")
    String helloNacos();
}
