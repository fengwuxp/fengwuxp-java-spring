package com.fengwuxp.wo.multiple;

import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * @Classname DefaultMultipleWxOpenServiceManager
 * @Description TODO
 * @Date 2020/3/16 19:30
 * @Created by 44487
 */
@Slf4j
@Setter
public class DefaultMultipleWxOpenServiceManager implements WxOpenServiceManager, BeanFactoryAware, InitializingBean {

    private Cache<String,WxOpenService> wxOpenServiceCache;

    protected BeanFactory beanFactory;

    private WxOpenConfigStorageProvider wxOpenConfigStorageProvider;

    private WeChatAppIdProvider weChatAppIdProvider;


    @Override
    public WxOpenService getWxOpenService() {
        return this.getWxOpenService(weChatAppIdProvider.getTargetAppId());
    }

    @Override
    public WxOpenService getWxOpenService(String appId) {

        return wxOpenServiceCache.get( appId,(key)->{

            WxOpenService wxOpenService = new WxOpenServiceImpl();
            WxOpenConfigStorage wxOpenConfigStorage = wxOpenConfigStorageProvider.getWxOpenConfigStorage(key);
            wxOpenService.setWxOpenConfigStorage(wxOpenConfigStorage);
            return wxOpenService;
        } );
    }

    @Override
    public void removeWxOpenService(String appId) {
        wxOpenServiceCache.invalidate(appId);
    }

    @Override
    public void clearAll() {
        wxOpenServiceCache.cleanUp();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if( this.weChatAppIdProvider == null ){
            this.weChatAppIdProvider = beanFactory.getBean(WeChatAppIdProvider.class);
        }

        if( this.wxOpenConfigStorageProvider == null){
            this.wxOpenConfigStorageProvider = beanFactory.getBean(WxOpenConfigStorageProvider.class);
        }

        WeChatMultipleProperties weChatMultipleProperties = beanFactory.getBean(WeChatMultipleProperties.class);

        this.wxOpenServiceCache = Caffeine.newBuilder()
                .maximumSize(weChatMultipleProperties.getMaxCacheSize())
                .build();
    }
}
