package com.fengwuxp.miniapp.multiple.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.fengwuxp.miniapp.multiple.DefaultMultipleWeChatMiniAppServiceManager;
import com.fengwuxp.miniapp.multiple.WeChatMiniAppServiceManager;
import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * @author wuxp
 */
@Configuration
@EnableConfigurationProperties(WeChatMultipleProperties.class)
@ConditionalOnProperty(prefix = WeChatMultipleProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeChatMinAppMultipleAutoConfiguration {


    private final WeChatMultipleProperties weChatMultipleProperties;

    public WeChatMinAppMultipleAutoConfiguration(WeChatMultipleProperties weChatMultipleProperties) {
        this.weChatMultipleProperties = weChatMultipleProperties;
    }

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
        return this.weChatServiceManager().getWxMaService();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_REQUEST
    )
    @RequestScope()
    public WxMaService weChatMiniAppRequestScopeService() {
        return this.weChatServiceManager().getWxMaService();
    }

    /**
     * 默认使用 SCOPE_SESSION
     * 由于此处的Bean实际上是从 {@link WeChatMiniAppServiceManager#getWxMaService()}中获取的
     * 因为目前 {@link WxMaService} 没有提供销毁的方法{@link Bean#destroyMethod()}，所有不会有问题
     *
     * @return WxMaBean
     */
    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_SESSION,
            matchIfMissing = true)
    @SessionScope()
    public WxMaService weChatMiniAppSessionScopeService() {
        return this.weChatServiceManager().getWxMaService();
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
