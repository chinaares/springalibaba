package com.example.nacosprovide.config;


import com.example.nacosprovide.component.BookServlet;
import com.example.nacosprovide.component.SystemFilter;
import com.example.nacosprovide.component.SystemListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.Arrays;

/**
 * Created by Administrator
 * 自定义配置类
 */
@Configuration
public class MyMvcConfig {
    /**
     * 注册 Servlet 三大组件 之  Servlet
     * 添加 ServletRegistrationBean ，就相当于以前在 web.xml 中配置的 <servlet></servlet>标签
     */
    @Bean
    public ServletRegistrationBean myServlet() {
        return new ServletRegistrationBean(new BookServlet(), "/bookServlet");
    }

    /**
     * 注册 Servlet 三大组件 之  Filter (过滤器)
     * 添加 FilterRegistrationBean ，就相当于以前在 web.xml 中配置的 <filter></filter> 标签
     */
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //同样添加自定义的 Filter
        registrationBean.setFilter(new SystemFilter());
        //然后设置过滤的路径，参数是个集合 ,相当于 web.xml中配置的 <filter-mapptin></filter-mapptin>
        // "/*": 表示过滤所有 get 与 post 请求
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        /*
         * setDispatcherTypes 相当于 web.xml 配置中 <filter-mapptin> 下的 <dispatcher> 标签
         * 用于过滤非常规的 get 、post 请求
         * REQUEST：默认方式，写了之后会过滤所有静态资源的请求
         * FORWARD：过滤所有的转发请求，无论是 jsp 中的 <jsp:forward</>、<%@ page errorPage= %>、还是后台的转发
         * INCLUDE：过滤 jsp 中的动态包含<jsp:include 请求
         * ERROR：过滤在 web.xml 配置的全局错误页面
         * 了解即可，实际中也很少这么做
         */
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return registrationBean;
    }

    /**
     * 注册 Servlet 三大组件 之  Listner
     * 添加 ServletListenerRegistrationBean ，就相当于以前在 web.xml 中配置的 <listener></listener>标签
     */
    @Bean
    public ServletListenerRegistrationBean myListener() {
        //ServletListenerRegistrationBean<T extends EventListener> 属于的是泛型，可以注册常见的任意监听器
        //将自己的监听器注册进来
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean(new SystemListener());
        return registrationBean;
    }
}