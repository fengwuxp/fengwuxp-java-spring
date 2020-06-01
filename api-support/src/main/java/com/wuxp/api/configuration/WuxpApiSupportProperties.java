package com.wuxp.api.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
}
