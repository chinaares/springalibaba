package com.example.scheduled.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class SwaggerConfiguration {

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

    @Bean(value = "nacosprovide")
    @Order(value = 1)
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.scheduled.over.controller"))
                .paths(PathSelectors.any())
                .build()
                //3.0以下版本
//                .globalOperationParameters(globalRequestParameters())
                //3.0以上版本
//                .globalRequestParameters(globalRequestParameters())
                .ignoredParameterTypes(HttpServletResponse.class, HttpServletRequest.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("标题")
                .description("描述").termsOfServiceUrl(contextPath + serverPort).contact(new Contact("Igor", "", ""))
                .version("1.0").build();
    }
    //3.0以下版本
//    private List<Parameter> globalRequestParameters() {
//        ParameterBuilder parameterBuilder = new ParameterBuilder()
//                //每次请求加载header
//                .parameterType("header")
//                //头标签
//                .name("Authorization")
//                .description("登录token")
//                .modelRef(new ModelRef("string"))
//                .required(false)
//                ;
//        return Collections.singletonList(parameterBuilder.build());
//    }
    //3.0以上版本
//    private List<RequestParameter> globalRequestParameters() {
//        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
//                //每次请求加载header
//                .in(ParameterType.HEADER)
//                //头标签
//                .name("Authorization")
//                .description("登录token")
//                .required(false)
//                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
//        return Collections.singletonList(parameterBuilder.build());
//    }

}
