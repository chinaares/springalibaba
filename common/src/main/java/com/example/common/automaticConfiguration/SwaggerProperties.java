package com.example.common.automaticConfiguration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-08-04 13:46:37
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * 是否启用swagger,生产环境建议关闭
     */
    private boolean enabled;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
}