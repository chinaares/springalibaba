package com.example.nacosfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker  //要在SpringCloud中使用断路器，需要加上@EnableCircuitBreaker注解：在对应的方法上加入@HystrixCommand注解实现断路器功能，当service方法对应的服务发生异常的时候，会跳转到serviceFallback方法执行
@EnableHystrixDashboard  // 开启dashboard，通过图形化的方式监控: 查看 http://127.0.0.1:8890/hystrix
public class NacosFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosFeignApplication.class, args);
	}

}
