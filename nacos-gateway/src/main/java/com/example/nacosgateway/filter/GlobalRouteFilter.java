package com.example.nacosgateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


//这个GlobalFilter从名字中就可以看出，是一个全局过滤器，也就是说实现这个接口后，所有的请求都会被过滤，我们就不需要在去找往某个路由中加过滤器了。
@Slf4j
@Component
@AllArgsConstructor
public class GlobalRouteFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("-----------------全局过滤器MyGlobalFilter---------------------\n");
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (StrUtil.isBlank(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    //设置过滤器的优先级，值越小优先级越高
    @Override
    public int getOrder() {
        return -99;
    }
}
