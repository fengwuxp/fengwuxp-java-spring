package com.oak.member.helper;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信小程序api操作
 *
 * @author laiy
 * create at 2020-02-06 19:31
 * @Description
 */
@Slf4j
@Component
public class WxMaHelper {

    //微信小程序参数
    //private static String appId = "wx794451f9a4fe0da2";
    private static String appId = "";
    private static String secret = "";
    private static String aesKey = "";
    private static String msgDataFormat = "";
    //微信公众号参数
    /**
     * 公众号appID
     */
    private static String mpAppId = "";
    /**
     * 公众号appsecret
     */
    private static String mpSecret = "";
    /**
     * 公众号EncodingAESKey
     */
    private static String mpAesKey = "";
    /**
     * 微信商户平台ID
     */
    private static String mpPartnerId = "";
    /**
     * 商户平台设置的API密钥
     */
    private static String mpPartnerKey = "";
    /**
     * 商户平台的证书文件地址
     */
    private static String mpKeyPath = "";
    /**
     * 模版消息的模版ID
     */
    private static String mpTemplateId = "";
    /**
     * 网页授权获取用户信息回调地址
     */
    private static String mpOauth2RedirectURI = "";
    /**
     * 网页应用授权登陆回调地址
     */
    private static String mpQRConnectRedirectURL = "";
    /**
     * 完整客服账号，格式为：账号前缀@公众号微信号
     */
    private static String mpKFAccount = "";

    @Value("${wechat.ma.appId}")
    public void setAppId(String appIdStr) {
        appId = appIdStr;
    }

    @Value("${wechat.ma.secret}")
    public void setSecret(String secretStr) {
        secret = secretStr;
    }

    @Value("${wechat.ma.aesKey}")
    public void setAesKey(String aeskey) {
        aesKey = aeskey;
    }

    @Value("${wechat.ma.msgDataFormat}")
    public void setMsgDataFormat(String msgdataFormat) {
        msgDataFormat = msgdataFormat;
    }

    @Value("${wechat.mp.appId}")
    public void setMpAppId(String appId) {
        mpAppId = appId;
    }

    @Value("${wechat.mp.secret}")
    public void setMpSecret(String secret) {
        mpSecret = secret;
    }

    @Value("${wechat.mp.aesKey}")
    public void setMpAesKey(String aesKey) {
        mpAesKey = aesKey;
    }

    @Value("${wechat.mp.partnerId}")
    public void setMpPartnerId(String mppartnerId) {
        mpPartnerId = mppartnerId;
    }

    @Value("${wechat.mp.partnerKey}")
    public void setMpPartnerKey(String mppartnerKey) {
        mpPartnerKey = mppartnerKey;
    }

    @Value("${wechat.mp.keyPath}")
    public void setMpKeyPath(String mpkeyPath) {
        mpKeyPath = mpkeyPath;
    }

    @Value("${wechat.mp.templateId}")
    public void setMpTemplateId(String mptemplateId) {
        mpTemplateId = mptemplateId;
    }

    @Value("${wechat.mp.oauth2RedirectURI}")
    public void setMpOauth2RedirectURI(String mpoauth2RedirectURI) {
        mpOauth2RedirectURI = mpoauth2RedirectURI;
    }

    @Value("${wechat.mp.qrConnectRedirectURL}")
    public void setMpQRConnectRedirectURL(String mpqRConnectRedirectURL) {
        mpQRConnectRedirectURL = mpqRConnectRedirectURL;
    }

    @Value("${wechat.mp.kfAccount}")
    public void setMpKFAccount(String mpkFAccount) {
        mpKFAccount = mpkFAccount;
    }

    /**
     * 获取 WxMaService 对象 (小程序)
     *
     * @return
     */
    public static WxMaService getWxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();

        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(appId);
        config.setSecret(secret);
        config.setAesKey(aesKey);
        config.setMsgDataFormat(msgDataFormat);

        wxMaService.setWxMaConfig(config);
        return wxMaService;
    }

    /**
     * 根据 临时登录凭证 获取用户唯一标识 OpenID 和 会话密钥 session_key
     *
     * @param jcode
     * @return
     */
    public static ApiResp<WxMaJscode2SessionResult> getWxMaSessionInfo(String jcode) {
        WxMaService wxMaService = getWxMaService();
        WxMaJscode2SessionResult sessionResult = null;
        try {
            sessionResult = wxMaService.getUserService().getSessionInfo(jcode);
            return RestfulApiRespFactory.ok(sessionResult);
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    /**
     * 解密用户敏感数据.
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param ivStr         加密算法的初始向量
     */
    public static ApiResp<WxMaUserInfo> getWxMaUserInfo(String sessionKey, String encryptedData, String ivStr) {
        WxMaService wxMaService = getWxMaService();
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, ivStr);
        return RestfulApiRespFactory.ok(userInfo);
    }

    /**
     * 解密用户手机号信息.
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param ivStr         加密算法的初始向量
     * @return .
     */
    public static ApiResp<WxMaPhoneNumberInfo> getWxMaPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
        WxMaService wxMaService = getWxMaService();
        WxMaPhoneNumberInfo phoneInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, ivStr);
        return RestfulApiRespFactory.ok(phoneInfo);
    }

    /**
     * 获取 WxMpService 对象 (公众号)
     *
     * @return
     */
    public static WxMpService getWxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(mpAppId);
        config.setSecret(mpSecret);
        //config.setAesKey(MP_AESKEY);
        //config.setAccessToken(MP_ACCESSTOKEN);
        //config.setToken(MP_TOKEN);
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

    /**
     * 获取用户网页授权 access_token
     *
     * @param code
     * @return
     * @throws WxErrorException
     */
    public static ApiResp<WxMpOAuth2AccessToken> getOAuth2AccessToken(String code) {
        WxMpService mpService = getWxMpService();
        WxMpOAuth2AccessToken token = null;
        try {
            token = mpService.oauth2getAccessToken(code);
            return RestfulApiRespFactory.ok(token);
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    /**
     * 微信公众号 根据token获取用户信息
     *
     * @param token 用户网页授权 access_token
     * @return
     */
    public static ApiResp<WxMpUser> getWxMpUserInfo(WxMpOAuth2AccessToken token) {
        WxMpService wxMpService = getWxMpService();

        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(token, null);
            return RestfulApiRespFactory.ok(wxMpUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    /**
     * 微信公众号 openId
     *
     * @param openId 用户网页授权 access_token
     * @return
     */
    public static ApiResp<WxMpUser> getWxMpUserInfo(String openId) {
        WxMpService mpService = WxMaHelper.getWxMpService();
        try {
            WxMpUser user = mpService.getUserService().userInfo(openId);
            return RestfulApiRespFactory.ok(user);
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        //WxMaJscode2SessionResult sessionResult = getWxMaSessionInfo("043591lC1GFqf70zdEjC1ug7lC1591l2");
        //System.out.println("sessionResult ---------->" + sessionResult.toString());
        //WxMpUser userInfo = getWxMpUserInfo("");

        //System.out.println(userInfo);

    }

}
