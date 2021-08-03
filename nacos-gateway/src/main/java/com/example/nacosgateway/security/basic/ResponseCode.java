package com.example.nacosgateway.security.basic;

import lombok.Getter;

@Getter
public enum ResponseCode implements IResponseCode {
    OK(1, "成功"),
    FAIL(0, "失败"),
    TOKEN_INVALID(401, "token无效"),
    SESSION_TIMEOUT(402, "会话超时"),
    FORBIDDEN_ACCESS(403, "访问未授权"),
    ACCOUNT_NOTFOUND(404, "账号不存在"),
    ACCOUNT_EXPIRED(405, "账号过期"),
    PASSWORD_EXPIRED(405, "密码过期"),
    LOGIN_INVALID(406, "账号或密码错误"),
    ACCOUNT_LOCKED(406, "账号锁定"),
    PARAMETER_INVALID(407, "参数错误"),
    BLOCK_EXCEPTION(408, "服务限流"),
    FALLBACK_EXCEPTION(409, "服务降级"),
    TOMANY_REQUEST_ERROR(410, "请求错误过多"),
    BACKGROUD_DEGRADE_ERROR(411, "服务降级"),
    BAD_GATEWAY(412, "错误的网关"),
    PARAMS_FAIL(400,"入参错误"),
    INTERNAL_SERVER_ERROR(500,"服务内部错误"),
    ECSC_CONNECTION_TIMEOUT(51001,"ecsc服务连接超时，请重试"),
    ECSC_GETDATA_FAIL(510002,"接口调用失败"),
    ECSC_REQUEST_PARAM_ERROR(510003,"接口请求参数异常"),
    ACCESS_LIMIT_REACHED(510004, "超出访问限制:{1}秒内限制访问{2}次"),
    ;
    int code;

    String msg;

    ResponseCode(int code) {
        this.code = code;
    }

    ResponseCode(int code, String msg) {
        this(code);
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
