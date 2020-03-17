package com.fengwuxp.wo.multiple.configuration;

import com.fengwuxp.wechat.multiple.HttpRequestWeChatServiceAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import com.fengwuxp.wo.multiple.DefaultMultipleWxOpenServiceManager;
import com.fengwuxp.wo.multiple.WxOpenServiceManager;
import me.chanjar.weixin.open.api.WxOpenService;
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

    @Autowired
    private WeChatMultipleProperties weChatMultipleProperties;

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
