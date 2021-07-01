package com.example.netty.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-01 13:31:28
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyProperties {
    //#请求队列长度
    private Integer sobacklog;
    //#工作线程组
    private Integer workGroupThreads;
    //监听端口
    private Integer port;
    //开启ssl(https)
    private Boolean openSSL;
}
