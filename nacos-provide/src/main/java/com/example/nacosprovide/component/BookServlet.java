package com.example.nacosprovide.component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by Administrator
 * 标准的 Servlet 实现 HttpServlet；重写其 doGet 、doPost 方法
 */
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(":com.example.nacosprovide.component.BookServlet:" + req.getRequestURL());
        //讲求转发到后台的 user/users 请求去，即会进入
        req.getRequestDispatcher("api/helloNacos").forward(req, resp);
    }
}