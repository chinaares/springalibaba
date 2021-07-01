package com.example.netty;

import com.example.netty.config.TaskSchedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
	}

	/**
	 * 开启启动任务
	 * @return
	 */
	@Bean
	public TaskSchedule taskRunner() {
		return new TaskSchedule();
	}

}
