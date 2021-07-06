package com.example.nacossentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NacosSentinelApplication {
	//java  -server -Xms64m -Xmx256m  -Dserver.port=8765 -Dcsp.sentinel.dashboard.server=localhost:8765 -Dproject.name=sentinel-dashboard -jar /work/sentinel-dashboard-1.7.1.jar
	public static void main(String[] args) {
		SpringApplication.run(NacosSentinelApplication.class, args);
	}

}
