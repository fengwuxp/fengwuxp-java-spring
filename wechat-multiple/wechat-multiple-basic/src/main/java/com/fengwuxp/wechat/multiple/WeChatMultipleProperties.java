package com.fengwuxp.multiple.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.context.WebApplicationContext;

/**
 * 微信小程序或公众号多个接入的配置
 */
@Data
@ConfigurationProperties(prefix = WeChatMultipleProperties.PREFIX)
public class WeChatMultipleProperties {


    public static final String PREFIX = "wechat.multiple";

    /**
     * service bean的 scope
     */
    private String beanScope = WebApplicationContext.SCOPE_SESSION;
}
