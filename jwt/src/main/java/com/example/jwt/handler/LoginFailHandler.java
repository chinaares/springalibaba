package com.example.jwt.handler;

import com.alibaba.fastjson.JSON;
import com.example.common.exception.basic.APIResponse;
import com.example.common.exception.basic.ResponseCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 登陆失败处理器
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException e) throws IOException, ServletException {
		APIResponse resultModel;
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

		if (e instanceof InternalAuthenticationServiceException) {
			//用户不存在
			resultModel = APIResponse.fail(ResponseCode.ACCOUNT_NOTFOUND);
		} else if (e instanceof BadCredentialsException) {
			//账号或密码错误
			resultModel = APIResponse.fail(ResponseCode.LOGIN_INVALID);
		} else if (e instanceof AccountExpiredException) {
			//账号过期
			resultModel = APIResponse.fail(ResponseCode.ACCOUNT_EXPIRED);
		} else if (e instanceof CredentialsExpiredException) {
			//密码过期
			resultModel = APIResponse.fail(ResponseCode.PASSWORD_EXPIRED);
		} else if (e instanceof DisabledException) {
			//账号不可用
			resultModel = APIResponse.fail(ResponseCode.ACCOUNT_NOTFOUND);
		} else if (e instanceof LockedException) {
			//账号锁定
			resultModel = APIResponse.fail(ResponseCode.ACCOUNT_LOCKED);
		}else {
			resultModel = APIResponse.fail(ResponseCode.FAIL);
		}
		response.getWriter().write(JSON.toJSONString(resultModel));
	}

}