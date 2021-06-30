package com.example.jwt.handler;

import com.alibaba.fastjson.JSON;
import com.example.common.exception.basic.APIResponse;
import com.example.common.exception.basic.ResponseCode;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当用户在没有授权的时候，返回的指定信息
 */
@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @SneakyThrows
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e){
        System.out.println("用户访问没有授权资源");
        APIResponse resultModel = APIResponse.fail(ResponseCode.FORBIDDEN_ACCESS);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(resultModel));

    }
}
