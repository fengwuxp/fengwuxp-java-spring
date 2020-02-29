package com.fengwuxp.wechat.multiple;

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


    /**
     * 缓存 微信相关服务的最大个数，小程序服务和公众号服务是独立缓存的
     */
    private Integer maxCacheSize = 200;
}
