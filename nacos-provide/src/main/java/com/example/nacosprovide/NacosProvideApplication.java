package com.example.nacosprovide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@MapperScan//指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类
public class NacosProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosProvideApplication.class, args);
	}

}
