package com.wuxp.miniprogram.services.utils;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Classname WxOpenUtils
 * @Description TODO
 * @Date 2020/3/17 19:20
 * @Created by 44487
 */
@Component
public class WxOpenUtils {
    @Autowired
    private static WxOpenService wxOpenService;



    public static String getAccessTokenByAppId(String appid) throws WxErrorException {
        return wxOpenService.getWxOpenComponentService().getAuthorizerAccessToken(appid, false);
    }

    public static WxOpenMaService getWxOpenMaService(String appid) {
        return wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
    }

    public static WxMpService getWxMpService(String appid) {
        return wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appid);
    }
}
