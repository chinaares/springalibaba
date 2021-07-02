package com.example.nacosfeign.config;


import com.example.nacosfeign.interceptor.LoginHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 实现 WebMvcConfigurer 接口，然后重启需要的方法，比如注册拦截器，则重写 addInterceptors(InterceptorRegistry registry)
 */
//@Configuration
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
                .excludePathPatterns("/api/feign/hello","/api/feign/user/index")
                .excludePathPatterns("/doc.html","/swagger-resources","/v2/api-docs","/webjars/**", "/**/*.css", "/**/*.js", "/fonts/**", "/images/**");
    }

    /**
     * 自定义资源映射
     * addResourceHandler(String... pathPatterns) : 添加静态资源映射路径，这些资源都不会被拦截.
     * addResourceLocations(String... resourceLocations)：添加静态资源路径
     * pathPatterns：虚拟路径/映射路径，即用户从前端请求的路径，如 http://ip:port/context-path/uploadFiles/1.mp4
     * resourceLocations：实际路径(结尾的斜杆不能省略)。可以是类路径，也可以是磁盘的实际路径，如 D:/wmx/mp4
     * classpath 表示类路径、file 表示磁盘路径
     * pathPatterns（虚拟路径）会自动映射到 resourceLocations（实际资源位置）
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/data/**").addResourceLocations("classpath:/data/");
        registry.addResourceHandler("/disk/**").addResourceLocations("file:///C:/Users/20111238/Pictures/Saved Pictures/");
    }
}