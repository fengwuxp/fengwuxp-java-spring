package com.fengwuxp.miniapp.multiple;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 用于支持多个小程序的服务
 *
 * @author wuxp
 */
@Slf4j
@Setter
public class DefaultMultipleWeChatMiniAppServiceManager implements WeChatMiniAppServiceManager, BeanFactoryAware, InitializingBean, DisposableBean {


    protected Cache<String, WxMaService> weChatMaServiceCache;

    protected BeanFactory beanFactory;

    private WeChatMaConfigProvider weChatMaConfigProvider;

    private WeChatAppIdProvider weChatAppIdProvider;


    @Override
    public WxMaService getWxMaService() {
        return this.getWxMaService(weChatAppIdProvider.getTargetAppId());
    }

    @Override
    public WxMaService getWxMaService(String appId) {

        return weChatMaServiceCache.get(appId, key -> {
            WxMaService service = new WxMaServiceImpl();
            service.setWxMaConfig(this.weChatMaConfigProvider.getWxMpConfigStorage(key));
            return service;
        });
    }

    @Override
    public void removeWxMpService(String appId) {
        weChatMaServiceCache.invalidate(appId);
    }


    @Override
    public void clearAll() {
        weChatMaServiceCache.cleanUp();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory factory = this.beanFactory;
        if (this.weChatAppIdProvider == null) {
            this.weChatAppIdProvider = factory.getBean(WeChatAppIdProvider.class);
        }
        if (this.weChatMaConfigProvider == null) {
            this.weChatMaConfigProvider = factory.getBean(WeChatMaConfigProvider.class);
        }
        WeChatMultipleProperties weChatMultipleProperties = factory.getBean(WeChatMultipleProperties.class);
        this.weChatMaServiceCache = Caffeine.newBuilder()
                .maximumSize(weChatMultipleProperties.getMaxCacheSize())
                .build();
    }

    @Override
    public void destroy() {
        this.clearAll();
    }
}
