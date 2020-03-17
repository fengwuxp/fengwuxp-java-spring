package com.wuxp.miniprogram.services.wxopenconfig;

import com.vma.wechatopen.gateway.config.IComponentConfigProvider;
import com.vma.wechatopen.gateway.config.WxOpenConfigEntity;
import com.wuxp.api.context.InjectField;
import com.wuxp.miniprogram.services.constant.WxOpenConfigConstant;
import com.wuxp.miniprogram.services.wxopenconfig.WxOpenConfigService;
import com.wuxp.miniprogram.services.wxopenconfig.info.WxOpenConfigInfo;
import com.wuxp.miniprogram.services.wxopenconfig.req.QueryWxOpenConfigReq;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

/**
 * @Classname WeChatOpenConfig
 * @Description 配置查询回调对象
 * @Date 2020/3/2 14:50
 * @Created by 44487
 */
@Component
@Slf4j
public class WeChatOpenConfig implements IComponentConfigProvider {

    @Autowired
    private WxOpenConfigService wxOpenConfigService;

    @Override
    public WxOpenConfigStorage getConfigByDomain(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) String organizationId) {

        WxOpenInMemoryConfigStorage wxOpenInMemoryConfigStorage = new WxOpenInMemoryConfigStorage();

        WxOpenConfigInfo wxOpenConfigInfo = getWxOpenConfigInfoById(organizationId);
        if( wxOpenConfigInfo == null ){
            return wxOpenInMemoryConfigStorage;
        }else {

            wxOpenInMemoryConfigStorage.setComponentAppId(wxOpenConfigInfo.getMiniProgramAppId());
            wxOpenInMemoryConfigStorage.setComponentAppSecret(wxOpenConfigInfo.getMiniProgramAppSecret());
            wxOpenInMemoryConfigStorage.setComponentToken(wxOpenConfigInfo.getMiniProgramToken());
            wxOpenInMemoryConfigStorage.setComponentAesKey(wxOpenConfigInfo.getMiniProgramMsgKey());

            return wxOpenInMemoryConfigStorage;
        }
    }

    @Override
    public WxOpenConfigEntity getConfigEntityByDomain(@InjectField(value = WxOpenConfigConstant.WXCONFIG_ORGANIZATION_ID_KEY) String organizationId) {

        WxOpenConfigEntity wxOpenConfigEntity = new WxOpenConfigEntity();

        WxOpenConfigInfo wxOpenConfigInfo = getWxOpenConfigInfoById(organizationId);
        if( wxOpenConfigInfo == null ){
            return wxOpenConfigEntity;
        }else {
            wxOpenConfigEntity.setComponentAppId(wxOpenConfigInfo.getMiniProgramAppId());
            wxOpenConfigEntity.setComponentAppSecret(wxOpenConfigInfo.getMiniProgramAppSecret());
            wxOpenConfigEntity.setComponentToken(wxOpenConfigInfo.getMiniProgramToken());
            wxOpenConfigEntity.setComponentAesKey(wxOpenConfigInfo.getMiniProgramMsgKey());
            return wxOpenConfigEntity;
        }
    }

    public WxOpenConfigInfo getWxOpenConfigInfoById(String organizationId){

        QueryWxOpenConfigReq queryWxOpenConfigReq = new QueryWxOpenConfigReq();
        queryWxOpenConfigReq.setOrganizationId( Long.valueOf( organizationId ));

        WxOpenConfigInfo wxOpenConfigInfo = wxOpenConfigService.query(queryWxOpenConfigReq).getFirst();
        return wxOpenConfigInfo;
    }



}
