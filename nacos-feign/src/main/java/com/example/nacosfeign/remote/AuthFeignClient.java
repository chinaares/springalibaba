package com.example.nacosfeign.remote;


import com.example.nacosfeign.fallback.AuthFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient( name="nocas-provide", fallback = AuthFallbackFactory.class)
public interface AuthFeignClient {

    @GetMapping("/api/helloNacos")
    String helloNacos();
}
