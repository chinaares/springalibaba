package com.example.nacosgateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.example.nacosgateway.mapper")
public class NacosGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosGatewayApplication.class, args);
	}

}
