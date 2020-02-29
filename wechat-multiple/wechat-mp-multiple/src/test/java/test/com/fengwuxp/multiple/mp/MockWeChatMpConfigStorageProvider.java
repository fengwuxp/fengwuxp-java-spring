package test.com.fengwuxp.multiple.mp;

import com.fengwuxp.mp.multiple.WeChatMpConfigStorageProvider;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

@Slf4j
public class MockWeChatMpConfigStorageProvider implements WeChatMpConfigStorageProvider {


    @Override
    public WxMpConfigStorage getWxMpConfigStorage(String appId) {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(appId);
        return wxMpDefaultConfig;
    }
}
