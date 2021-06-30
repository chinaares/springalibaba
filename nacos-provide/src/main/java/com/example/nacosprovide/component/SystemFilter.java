package com.example.nacosprovide.component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 * Created by Administrator
 * 标准 Servlet 过滤器，实现 javax.servlet.Filter 接口
 * 并重写它的 三个方法
 */
public class SystemFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("javax.servlet.Filter：：服务器启动....");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 转为 HttpServletRequest 输出请求路径 容易查看 请求地址
         */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("javax.servlet.Filter：：过滤器放行前...." + request.getRequestURL());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("javax.servlet.Filter：：过滤器返回后...." + request.getRequestURL());
    }
    @Override
    public void destroy() {
        System.out.println("javax.servlet.Filter：：服务器关闭....");
    }
}