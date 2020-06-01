package com.fengwuxp.miniapp.multiple.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.fengwuxp.miniapp.multiple.DefaultMultipleWeChatMiniAppServiceManager;
import com.fengwuxp.miniapp.multiple.WeChatMiniAppServiceManager;
import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WeChatMinAppMultipleAutoConfiguration {


    @Autowired
    private WeChatMultipleProperties weChatMultipleProperties;

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
    public WxMaService weChatMiniAppSingletonScopeService() {
        return this.weChatServiceManager().getWxMpService();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_REQUEST
    )
    @RequestScope()
    public WxMaService weChatMiniAppRequestScopeService() {
        return this.weChatServiceManager().getWxMpService();
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
    public WxMaService weChatMiniAppSessionScopeService() {
        return this.weChatServiceManager().getWxMpService();
    }

    @ConditionalOnMissingBean
    @Bean
    public WeChatAppIdProvider weChatAppIdProvider() {
        return new HttpRequestWeChatServiceAppIdProvider(weChatMultipleProperties.getAppIdHeaderName());
    }

    @Bean
    @ConditionalOnMissingBean
    public WeChatMiniAppServiceManager weChatServiceManager() {

        return new DefaultMultipleWeChatMiniAppServiceManager();
    }
}
