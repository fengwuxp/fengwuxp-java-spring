package com.fengwuxp.multiple.mp.configuration;

import com.fengwuxp.multiple.wechat.HttpRequestWeChatServiceAppIdProvider;
import com.fengwuxp.multiple.mp.DefaultMultipleWeChatMpServiceManager;
import com.fengwuxp.multiple.mp.WeChatMpServiceManager;
import com.fengwuxp.multiple.wechat.WeChatAppIdProvider;
import com.fengwuxp.multiple.wechat.WeChatMultipleProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
@EnableConfigurationProperties(WeChatMultipleProperties.class)
@ConditionalOnProperty(prefix = WeChatMultipleProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeChatMpMultipleAutoConfiguration {


    /**
     * only test
     */
    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = SCOPE_SINGLETON
    )
    @ConditionalOnMissingBean
    public WxMpService wxMpSingletonScopeService() {
        return this.weChatMpServiceManager().getWxMpService();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_REQUEST
    )
    @RequestScope()
    public WxMpService wxMpRequestScopeService() {
        return this.weChatMpServiceManager().getWxMpService();
    }

    /**
     * 默认使用 SCOPE_SESSION
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_SESSION,
            matchIfMissing = true)
    @SessionScope()
    public WxMpService wxMpSessionScopeService() {
        return this.weChatMpServiceManager().getWxMpService();
    }

    @ConditionalOnMissingBean
    @Bean
    public WeChatAppIdProvider weChatAppIdProvider() {
        return new HttpRequestWeChatServiceAppIdProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public WeChatMpServiceManager weChatMpServiceManager() {

        return new DefaultMultipleWeChatMpServiceManager();
    }
}
