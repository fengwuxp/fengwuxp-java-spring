package com.oak.member.management.third;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.oak.member.management.third.info.WxSessionInfo;
import com.oak.member.management.third.info.WxUserInfo;
import com.oak.member.management.third.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdServiceImpl implements ThirdService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMaService wxMaService;

    @Override
    public ApiResp<String> getWxUnionID(GetWxUnionIDReq evt) {
        WxMpOAuth2AccessToken oAuth2AccessToken = new WxMpOAuth2AccessToken();
        oAuth2AccessToken.setAccessToken(evt.getAccessToken());
        oAuth2AccessToken.setOpenId(evt.getOpenid());
        try {
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(oAuth2AccessToken, null);
            return RestfulApiRespFactory.ok(wxMpUser.getUnionId());
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return RestfulApiRespFactory.error(e.getMessage());
        }
    }

    @Override
    public ApiResp<WxUserInfo> getWxUserInfo(GetWxUserInfoReq evt) {
        WxUserInfo wxUserInfo = new WxUserInfo();
        try {
            WxMpOAuth2AccessToken token = wxMpService.oauth2getAccessToken(evt.getCode());
            WxMpUser userInfo = wxMpService.oauth2getUserInfo(token, null);

            BeanUtils.copyProperties(userInfo, wxUserInfo);
        }catch (WxErrorException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return RestfulApiRespFactory.error(e.getMessage());
        }
        return RestfulApiRespFactory.ok(wxUserInfo);
    }

    @Override
    public ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq evt) {
        WxUserInfo wxUserInfo = new WxUserInfo();
        WxMaUserInfo userInfo = wxMaService.getUserService()
                .getUserInfo(evt.getSessionKey(), evt.getEncryptedData(), evt.getIvStr());
        BeanUtils.copyProperties(userInfo, wxUserInfo);
        return RestfulApiRespFactory.ok(wxUserInfo);
    }

    @Override
    public ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq evt) {
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService()
                .getPhoneNoInfo(evt.getSessionKey(), evt.getEncryptedData(), evt.getIvStr());
        return RestfulApiRespFactory.ok(phoneNoInfo.getPhoneNumber());
    }

    @Override
    public ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq evt) {
        WxSessionInfo wxSessionInfo = new WxSessionInfo();
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService()
                    .getSessionInfo(evt.getCode());
            BeanUtils.copyProperties(sessionInfo, wxSessionInfo);
        } catch (WxErrorException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return RestfulApiRespFactory.error("获取微信会话信息失败");
        }
        return RestfulApiRespFactory.ok(wxSessionInfo);
    }

}
