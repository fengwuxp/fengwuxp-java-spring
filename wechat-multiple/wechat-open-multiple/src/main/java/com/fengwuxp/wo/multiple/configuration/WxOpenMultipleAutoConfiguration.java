package com.fengwuxp.wo.multiple.configuration;

import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import com.fengwuxp.wo.multiple.DefaultMultipleWxOpenServiceManager;
import com.fengwuxp.wo.multiple.WxOpenServiceManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenService;
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
 * @Classname WxOpenMultipleAutoConfiguration
 * @Description TODO
 * @Date 2020/3/16 19:58
 * @Created by 44487
 */
@Configuration
@EnableConfigurationProperties(WeChatMultipleProperties.class)
@ConditionalOnProperty(prefix = WeChatMultipleProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WxOpenMultipleAutoConfiguration {

    private final WeChatMultipleProperties weChatMultipleProperties;

    public WxOpenMultipleAutoConfiguration(WeChatMultipleProperties weChatMultipleProperties) {
        this.weChatMultipleProperties = weChatMultipleProperties;
    }

    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = SCOPE_SINGLETON
    )
    @ConditionalOnMissingBean
    public WxOpenService wxOpenSingletonScopeService(){
        return this.wxOpenServiceManager().getWxOpenService();
    }

    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_REQUEST
    )
    @RequestScope()
    public WxOpenService wxOpenRequestScopeService(){
        return this.wxOpenServiceManager().getWxOpenService();
    }

    /**
     * 默认使用 SCOPE_SESSION
     * 由于此处的Bean实际上是从 {@link WxOpenServiceManager#getWxOpenService()}中获取的
     * 因为目前 {@link WxOpenService} 没有提供销毁的方法{@link Bean#destroyMethod()}，所有不会有问题
     * @return WxOpenService
     */
    @Bean
    @ConditionalOnProperty(
            prefix = WeChatMultipleProperties.PREFIX,
            name = "bean-scope",
            havingValue = WebApplicationContext.SCOPE_SESSION,
            matchIfMissing = true)
    @SessionScope()
    public WxOpenService wxOpenSessionScopeService(){
        return this.wxOpenServiceManager().getWxOpenService();
    }

    @ConditionalOnMissingBean
    @Bean
    public WeChatAppIdProvider weChatAppIdProvider() {
        return new HttpRequestWeChatServiceAppIdProvider(weChatMultipleProperties.getAppIdHeaderName());
    }

    @Bean
    @ConditionalOnMissingBean
    public WxOpenServiceManager wxOpenServiceManager(){
        return new DefaultMultipleWxOpenServiceManager();
    }

}
