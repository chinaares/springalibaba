package com.example.common.exception;

import cn.hutool.core.util.StrUtil;

import com.example.common.exception.basic.IResponseCode;
import com.example.common.exception.basic.ResponseCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 信息参数
     */
    private Object[] args;
    /**
     * 英文错误信息
     */
    private IResponseCode responseCode;

    public BusinessException() {
        this.code = ResponseCode.FAIL.getCode();
    }

    /**
     * 已废弃，建议使用 BaseException(IResponseCode responseCode)，否则无法支持多语化
     *
     * @param message
     */
    @Deprecated
    public BusinessException(String message) {
        this(ResponseCode.FAIL.getCode(), message);
        setResponseCode(ResponseCode.FAIL);
    }

    @Deprecated
    public BusinessException(String fmt, Object... info) {
        this(ResponseCode.FAIL.getCode(), StrUtil.format(fmt, info));
        setResponseCode(ResponseCode.FAIL);
    }

    public BusinessException(IResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMsg());
        setResponseCode(responseCode);
    }

    public BusinessException(IResponseCode responseCode, Object... args) {
        this(responseCode.getCode(), responseCode.getMsg());
        setArgs(args);
        setResponseCode(responseCode);
    }

    /**
     * 该方式无法支撑多语化，推荐使用 BaseException(IResponseCode responseCode)
     *
     * @param code    异常码
     * @param message 异常信息
     */
    private BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
