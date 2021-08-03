package com.example.nacosgateway.security.handler;

import cn.hutool.json.JSONUtil;
import com.example.nacosgateway.security.basic.APIResponse;
import com.example.nacosgateway.security.basic.ResponseCode;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 无访问权限的返回结果
 *
 */
@Component
@Slf4j
public class AuthEntryPointException implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        log.info("无访问权限的返回结果");
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        String body = JSONUtil.toJsonStr(APIResponse.fail(ResponseCode.FORBIDDEN_ACCESS));
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(body.getBytes(CharsetUtil.UTF_8));
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }
}