package com.wuxp.security.example.wechat;

import cn.binarywang.wx.miniapp.config.WxMaConfig;
import com.fengwuxp.miniapp.multiple.WeChatMaConfigProvider;
import org.springframework.stereotype.Component;

/**
 * @author wuxp
 */
@Component
public class MockWeChatMaConfigProvider implements WeChatMaConfigProvider {

    @Override
    public WxMaConfig getWxMpConfigStorage(String appId) {
        return null;
    }
}
