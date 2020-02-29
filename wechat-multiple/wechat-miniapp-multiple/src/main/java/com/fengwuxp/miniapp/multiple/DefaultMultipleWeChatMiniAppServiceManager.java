package com.fengwuxp.miniapp.multiple;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;


/**
 * 用于支持多个小程序的服务
 */
@Slf4j
@Setter
public class DefaultMultipleWeChatMiniAppServiceManager implements WeChatMiniAppServiceManager, BeanFactoryAware, InitializingBean {


    protected Cache<String, WxMaService> wxMaServiceCache = Caffeine.newBuilder()
            .maximumSize(200)
            .build();

    protected BeanFactory beanFactory;

    private WeChatMaConfigProvider weChatMaConfigProvider;

    private WeChatAppIdProvider weChatAppIdProvider;


    @Override
    public WxMaService getWxMpService() {
        return this.getWxMpService(weChatAppIdProvider.getTargetAppId());
    }

    @Override
    public WxMaService getWxMpService(String appId) {

        return wxMaServiceCache.get(appId, (key) -> {
            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(this.weChatMaConfigProvider.getWxMpConfigStorage(appId));
            return service;
        });
    }

    @Override
    public void removeWxMpService(String appId) {
        wxMaServiceCache.invalidate(appId);
    }


    @Override
    public void clearAll() {
        wxMaServiceCache.cleanUp();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.weChatAppIdProvider == null) {
            this.weChatAppIdProvider = beanFactory.getBean(WeChatAppIdProvider.class);
        }
        if (this.weChatMaConfigProvider == null) {
            this.weChatMaConfigProvider = beanFactory.getBean(WeChatMaConfigProvider.class);
        }
    }
}
