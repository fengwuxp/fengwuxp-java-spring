package com.wuxp.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxp
 */
@Data
@ConfigurationProperties(prefix = WuxpApiSupportProperties.PREFIX)
public class WuxpApiSupportProperties {

    public static final String PREFIX = "wuxp.api";

    /**
     * 是否启用api支持
     */
    private Boolean enabled = true;

    /**
     * 是否启用api签名策略
     */
    private Boolean enabledApiSignature = true;

    /**
     * 是否启用数据初始化器
     * 一般应用在生产正常部署后可以考虑关闭该配置
     */
    private Boolean enabledInitiator = true;

    /**
     * 启用restful 风格的api支持
     */
    private Boolean enabledRestful = false;

    /**
     * 启用多版本api支持
     * {@link com.wuxp.api.version.ApiVersion}
     */
    private Boolean enabledApiVersion = false;
}
