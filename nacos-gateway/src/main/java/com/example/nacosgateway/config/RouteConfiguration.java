package com.example.nacosgateway.config;

import com.example.nacosgateway.filter.TokenGatewayFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class RouteConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        log.info("ServerGatewayFilter filtet........");
        return builder.routes()
                .route(r -> r.path("/demo/**")
                .filters(f -> f.stripPrefix(1).filters(new TokenGatewayFilter()))
                .uri("lb://cloud-discovery-server"))
                .build();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/csdn").uri("https://blog.csdn.net"))
                .build();
    }
}