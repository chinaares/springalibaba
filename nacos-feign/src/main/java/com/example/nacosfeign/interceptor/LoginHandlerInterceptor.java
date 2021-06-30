package com.example.nacosfeign.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author wangmaoxiong
 * @version 1.0
 * @date 2020/4/30 17:18
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 请求进入时拦截，返回 true 时，表示继续往下走；返回 false 表示停止后续的执行，即请求不会到达控制层.
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截请求：" + request.getRequestURL());
        //getSession()：返回与此请求关联的当前会话，或者如果该请求没有会话，则创建一个会话。
        Object isLogin = request.getSession().getAttribute("isLogin");
        //不为 null，则说明一次会话内，已经请求过 /user/index，否则就让它先去请求 /user/index
        if (isLogin == null) {
            //重定向到 /user/index
            response.sendRedirect("/api/feign/hello");
            return false;
        }
        return true;
    }

}