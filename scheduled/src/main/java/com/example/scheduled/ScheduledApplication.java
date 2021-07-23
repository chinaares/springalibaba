package com.example.scheduled;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.scheduled.over.mapper")
public class ScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduledApplication.class, args);
	}

}
