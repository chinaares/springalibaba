package com.example.springbootbase.basic;

import com.example.common.exception.basic.IResponseCode;
import lombok.Getter;

@Getter
public enum ResponseCode implements IResponseCode {
    OK(1, "成功"),
    FAIL(0, "失败"),
    ACCESS_LIMIT_REACHED(401, "超出访问限制:{1}秒内访问{2}次"),
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
