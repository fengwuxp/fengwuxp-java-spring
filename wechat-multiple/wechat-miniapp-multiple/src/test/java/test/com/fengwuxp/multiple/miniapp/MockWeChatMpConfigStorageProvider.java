package test.com.fengwuxp.multiple.miniapp;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.fengwuxp.miniapp.multiple.WeChatMaConfigProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockWeChatMpConfigStorageProvider implements WeChatMaConfigProvider {


    @Override
    public WxMaConfig getWxMpConfigStorage(String appId) {
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(appId);
        return wxMaConfig;
    }
}
