package com.example.security.handler;

import com.alibaba.fastjson.JSON;
import com.example.common.exception.basic.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆成功处理器
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		//处理中文乱码以及跨域问题
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		//得到用户
		User user = (User)authentication.getPrincipal();

		APIResponse resultModel = APIResponse.ok(user);
		//以流的方式写数据到前端
		response.getWriter().write(JSON.toJSONString(resultModel));

	}

}