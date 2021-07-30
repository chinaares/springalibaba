package com.example.springbootbase.interceptor;


import com.alibaba.fastjson.JSON;
import com.example.common.exception.basic.APIResponse;
import com.example.springbootbase.basic.ResponseCode;
import com.example.springbootbase.config.AccessLimit;
import com.example.springbootbase.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Component
//@ComponentScan(basePackages={"com.example.redis.utils"})
public class AntiBrushInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            if (login) {
                //获取登录的session进行判断
                //.....
                key += "" + "1";  //这里假设用户是1,项目中是动态获取的userId
            }

            //从redis中获取用户访问的次数
            Integer count = redisUtil.getObject(key);
            if(count == null){
                //第一次访问
                redisUtil.set(key,1,seconds);
            }else if (count < maxCount) {
                //加1
                redisUtil.incr(key, 1);
            } else {
                //超出访问次数
                render(response,seconds,maxCount);
                return false;
            }
        }

        return true;

    }

    private void render(HttpServletResponse response,int seconds,int maxCount) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(APIResponse.fail(ResponseCode.ACCESS_LIMIT_REACHED));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}