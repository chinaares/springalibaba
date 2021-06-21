package com.example.nacosfeign.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Value("${server.port:8080}")
    private Integer serverPort;
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean(value = "monitoringApi")
    @Order(value = 1)
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.nacosprovide.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalRequestParameters())
                .ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("标题")
                .description("描述").termsOfServiceUrl(contextPath + serverPort).contact(new Contact("Igor", "", ""))
                .version("1.0").build();
    }

    private List<Parameter> globalRequestParameters() {
        ParameterBuilder parameterBuilder = new ParameterBuilder()
                //每次请求加载header
                .parameterType("header")
                //头标签
                .name("Authorization")
                .description("登录token")
                .modelRef(new ModelRef("string"))
                .required(false)
                ;
        return Collections.singletonList(parameterBuilder.build());
    }

}
