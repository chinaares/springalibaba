package com.example.nacosfeign.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private Integer serverPort;
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public ApplicationRunner swaggerRunner() {
        return (arguments) -> {
            log.info("LOCAL DOC：http://localhost:{}{}/doc.html", this.serverPort, this.contextPath);
        };
    }

}
