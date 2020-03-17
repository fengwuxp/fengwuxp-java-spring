package com.wuxp.miniprogram.services.config;

import com.fengwuxp.wo.multiple.WxOpenConfigStorageProvider;
import com.wuxp.miniprogram.services.wxopenconfig.WxOpenConfigService;
import com.wuxp.miniprogram.services.wxopenconfig.info.WxOpenConfigInfo;
import com.wuxp.miniprogram.services.wxopenconfig.req.QueryWxOpenConfigReq;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname WxOpenConfigStorageProviderImpl
 * @Description TODO
 * @Date 2020/3/17 15:20
 * @Created by 44487
 */

@Component
@Slf4j
public class WxOpenConfigStorageProviderImpl implements WxOpenConfigStorageProvider {

    @Autowired
    private WxOpenConfigService wxOpenConfigService;

    @Override
    public WxOpenConfigStorage getWxOpenConfigStorage(String appId) {
        WxOpenInMemoryConfigStorage wxOpenInMemoryConfigStorage = new WxOpenInMemoryConfigStorage();

        WxOpenConfigInfo wxOpenConfigInfo = getWxOpenConfigInfoByAppId(appId);
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

    public WxOpenConfigInfo getWxOpenConfigInfoByAppId(String appId){

        QueryWxOpenConfigReq queryWxOpenConfigReq = new QueryWxOpenConfigReq();
        queryWxOpenConfigReq.setMiniProgramAppId(appId);

        WxOpenConfigInfo wxOpenConfigInfo = wxOpenConfigService.query(queryWxOpenConfigReq).getFirst();
        return wxOpenConfigInfo;
    }
}
