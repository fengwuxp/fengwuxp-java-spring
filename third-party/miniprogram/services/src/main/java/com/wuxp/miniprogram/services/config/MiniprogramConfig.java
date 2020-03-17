package com.wuxp.miniprogram.services.config;

import com.fengwuxp.wo.multiple.configuration.WxOpenMultipleAutoConfiguration;
import com.vma.wechatopen.service.business.service.IAuthCallService;
import com.vma.wechatopen.service.business.service.IServerCheckService;
import com.vma.wechatopen.service.business.service.impl.AuthCallServiceImpl;
import com.vma.wechatopen.service.business.service.impl.ServerCheckServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Classname MiniprogramConfig
 * @Description TODO
 * @Date 2020/3/16 20:24
 * @Created by 44487
 */
@Configuration
public class MiniprogramConfig {

    /**
     * 授权回调接口
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IAuthCallService authCall() {
        return new AuthCallServiceImpl();
    }

    /**
     * 服务校验
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public IServerCheckService serverCheckService() {
        return new ServerCheckServiceImpl();
    }


}
