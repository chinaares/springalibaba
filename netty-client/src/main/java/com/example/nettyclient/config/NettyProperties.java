package com.example.nettyclient.config;

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
    //#监听端口
    private Integer portHTTP;
    //#监听地址
    private String host;
}
