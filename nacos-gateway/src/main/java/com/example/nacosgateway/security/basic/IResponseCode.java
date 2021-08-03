package com.example.nacosgateway.security.basic;

public interface IResponseCode {

    /**
     * 获取响应码
     * @return
     */
    int getCode();

    /**
     * 获取响应信息
     *
     * @return
     */
    String getMsg();

}
