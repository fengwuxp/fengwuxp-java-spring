package com.fengwuxp.mp.multiple;

import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;


/**
 * 用于支持多公众号的服务
 */
@Slf4j
@Setter
public class DefaultMultipleWeChatMpServiceManager implements WeChatMpServiceManager, BeanFactoryAware, InitializingBean {


    private Cache<String, WxMpService> WE_MAP_SERVICE_CACHE = Caffeine.newBuilder()
            .maximumSize(200)
            .build();

    protected BeanFactory beanFactory;

    private WeChatMpConfigStorageProvider weChatMpConfigStorageProvider;

    private WeChatAppIdProvider weChatAppIdProvider;


    @Override
    public WxMpService getWxMpService() {
        return this.getWxMpService(weChatAppIdProvider.getTargetAppId());
    }

    @Override
    public WxMpService getWxMpService(String appId) {

        return WE_MAP_SERVICE_CACHE.get(appId, (key) -> {
            WxMpService service = new WxMpServiceImpl();
            WxMpConfigStorage wxMpConfigStorage = weChatMpConfigStorageProvider.getWxMpConfigStorage(key);
            service.setWxMpConfigStorage(wxMpConfigStorage);
            return service;
        });
    }

    @Override
    public void removeWxMpService(String appId) {
        WE_MAP_SERVICE_CACHE.invalidate(appId);
    }


    @Override
    public void clearAll() {
        WE_MAP_SERVICE_CACHE.cleanUp();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.weChatAppIdProvider == null) {
            this.weChatAppIdProvider = beanFactory.getBean(WeChatAppIdProvider.class);
        }
        if (this.weChatMpConfigStorageProvider == null) {
            this.weChatMpConfigStorageProvider = beanFactory.getBean(WeChatMpConfigStorageProvider.class);
        }
    }
}
