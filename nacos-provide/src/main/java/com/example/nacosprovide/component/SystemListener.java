package com.example.nacosprovide.component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * Created by Administrator
 * 标准 Servlet 监听器，实现 javax.servlet.ServletContextListener 接口
 * 然后实现方法
 * ServletContextListener：属于 Servlet 应用启动关闭监听器，监听容器初始化与销毁
 */
public class SystemListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("com.example.nacosprovide.component.SystemListener::服务器启动.....");
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("com.example.nacosprovide.component.SystemListener::服务器关闭.....");
    }
}