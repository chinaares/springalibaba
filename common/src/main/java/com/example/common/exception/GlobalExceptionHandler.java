package com.example.common.exception;

import com.example.common.exception.basic.APIResponse;
import com.example.common.exception.basic.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @Resource
    MessageSource messageSource;

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public APIResponse processRequestParameterException(
            HttpServletRequest request,
            HttpServletResponse response,
            MissingServletRequestParameterException e) {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        APIResponse result = new APIResponse();
        result.setCode(ResponseCode.FAIL.getCode());
        result.setMsg(
                messageSource.getMessage(ResponseCode.FAIL.getMsg(),
                        null, LocaleContextHolder.getLocale()) + e.getParameterName());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public APIResponse processDefaultException(HttpServletResponse response,
                                               Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        APIResponse result = new APIResponse();
        result.setCode(ResponseCode.FAIL.getCode());
        result.setMsg(e.getMessage());
        return APIResponse.fail(result.getCode(), result.getMsg());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public APIResponse processApiException(
            HttpServletRequest request,
            HttpServletResponse response,
            BusinessException e) {
        log.error("服务器内部错误，异常接口地址：{}, 参数：{}", request.getRequestURI(), request.getParameterMap().toString());
        APIResponse result = new APIResponse();
        result.setCode(ResponseCode.FAIL.getCode());
        result.setMsg(e.getArgs()[0].toString());
        return APIResponse.fail(result.getCode(), result.getMsg());
    }

}
