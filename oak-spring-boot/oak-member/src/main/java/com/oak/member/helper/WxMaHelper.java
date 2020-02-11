package com.oak.member.helper;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

/**
 * 微信小程序api操作
 * @author laiy
 * create at 2020-02-06 19:31
 * @Description
 */
@Slf4j
@Component
public class WxMaHelper {

    //private static String appId = "wx794451f9a4fe0da2";
    private static String appId = "wxf7aa3f25069cf527";
    private static String secret = "82dfc32a790aa3e66547935d8ae7a3a6";
    private static String token = "";
    private static String aesKey = "EncodingAESKey";
    private static String msgDataFormat = "JSON";

    /**
     * 获取 WxMaService 对象
     * @return
     */
    public static WxMaService getWxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appId);
        config.setSecret(secret);
        config.setToken(token);
        config.setAesKey(aesKey);
        config.setMsgDataFormat(msgDataFormat);

        wxMaService.setWxMaConfig(config);
        return wxMaService;
    }

    /**
     * 根据 临时登录凭证 获取用户唯一标识 OpenID 和 会话密钥 session_key
     * @param jcode
     * @return
     */
    public static WxMaJscode2SessionResult getWxSessionInfo(String jcode) {
        WxMaService wxMaService = getWxMaService();
        WxMaJscode2SessionResult sessionResult = null;
        try {
            sessionResult = wxMaService.getUserService().getSessionInfo(jcode);
        } catch (WxErrorException e) {
            log.error(e.getMessage());

        }
        return sessionResult;
    }

    /**
     * 解密用户敏感数据.
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param ivStr         加密算法的初始向量
     */
    public static WxMaUserInfo getWxUserInfo(String sessionKey, String encryptedData, String ivStr) {
        WxMaService wxMaService = getWxMaService();
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, ivStr);
        return userInfo;
    }

    /**
     * 解密用户手机号信息.
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param ivStr         加密算法的初始向量
     * @return .
     */
    public static WxMaPhoneNumberInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
        WxMaService wxMaService = getWxMaService();
        WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, ivStr);
        return phoneInfo;
    }

    public static void main(String[] args) {
        WxMaJscode2SessionResult sessionResult = getWxSessionInfo("043591lC1GFqf70zdEjC1ug7lC1591l2");
        System.out.println("sessionResult ---------->" + sessionResult.toString());
        WxMaUserInfo userInfo = getWxUserInfo(sessionResult.getSessionKey(),
                "66ka+f0iSF34biWvx/6vNQp4LVEmAZubifUfr+/2Qzt/gloG1GwT/3sLpychKEZPEcwaecJ8lNHyloyhWGGEbkSrO4TyxsWe9sUEeH7bxawHRy134nmnQqPyWM/0dsVQY9e6ZicLVt9rgQLhVhNgO8uY1Wmw7bd4jgA+yRWG0rwwu6v8rpO9D900Gj9qfhCSxbAigoWWHOvYxixsd7f0Van9pKadEgTWcnS2x+mG4VbtO08I1e3hSkwGu0oZHrFbq6KEyUSJjELBYC6kMCHS5WA7kTmq01R/T4AJ/QsMcBL84J5sASCpmzUJ2pwnI1kk8z70U0e5v6Eg1yvWMXKTTPjl2Y+PkmF4WVqJrIGK/TDmnFI88NUr1MGRc8v1srZVeZVKf7Rv9Q4TA8dv6W0P7cBkRyTG/dOBdU8RtzHn/S6U4N203YPKkg+/5lFlrlsTJeq57Yn6VJoL0Uv0rBIMJw==",
                "7xgw6nWHI+etEXCYHHDDVQ==");

        System.out.println(userInfo);

    }

}
