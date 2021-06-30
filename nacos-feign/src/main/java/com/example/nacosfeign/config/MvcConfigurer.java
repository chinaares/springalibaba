package com.example.nacosfeign.config;


import com.example.nacosfeign.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author wangmaoxiong
 * @version 1.0
 * @date 2020/4/30 17:25
 * 实现 WebMvcConfigurer 接口，然后重启需要的方法，比如注册拦截器，则重写 addInterceptors(InterceptorRegistry registry)
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    /**
     * 注册拦截器
     * .addPathPatterns("/**")：表示拦截整个应用中的所有请求
     * .excludePathPatterns(String... patterns)：表示排除这些规则的请求，不对它们进行拦截
     * <p>
     * spring Boot 2 以后，静态资源也会被拦截.
     * classpath:/META‐INF/resources/","classpath:/resources/","classpath:/static/","classpath:/public/"下的资源也会被拦截
     * 通常静态资源可以不需要进行拦截，可以对它们直接进行放行
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/feign/hello")
                .excludePathPatterns("/webjars/**", "/css/**/*.css", "/js/**/*.js", "/fonts/**", "/images/**");
    }
}