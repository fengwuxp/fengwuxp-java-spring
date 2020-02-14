package com.oak.member.management.third;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.oak.member.helper.WxMaHelper;
import com.oak.member.management.third.info.WxSessionInfo;
import com.oak.member.management.third.info.WxUserInfo;
import com.oak.member.management.third.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThirdServiceImpl implements ThirdService {

    WxMpService wxOpenMpService;

    @Override
    public ApiResp<String> getWxUnionID(GetWxUnionIDReq evt) {

        WxMpOAuth2AccessToken oAuth2AccessToken = new WxMpOAuth2AccessToken();
        oAuth2AccessToken.setAccessToken(evt.getAccessToken());
        oAuth2AccessToken.setOpenId(evt.getOpenid());
        ApiResp<WxMpUser> wxMpUser = WxMaHelper.getWxMpUserInfo(oAuth2AccessToken);
        AssertThrow.assertResp(wxMpUser);

        return RestfulApiRespFactory.ok(wxMpUser.getData().getUnionId());
    }

    @Override
    public ApiResp<WxUserInfo> getWxUserInfo(GetWxUserInfoReq evt) {
        ApiResp<WxMpOAuth2AccessToken> tokenResp = WxMaHelper.getOAuth2AccessToken(evt.getCode());
        AssertThrow.assertResp(tokenResp);
        ApiResp<WxMpUser> userResp = WxMaHelper.getWxMpUserInfo(tokenResp.getData());
        AssertThrow.assertResp(userResp);

        WxUserInfo wxUserInfo = new WxUserInfo();
        BeanUtils.copyProperties(userResp.getData(), wxUserInfo);

        return RestfulApiRespFactory.ok(wxUserInfo);
    }

    @Override
    public ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq evt) {
        WxUserInfo wxUserInfo = new WxUserInfo();
        ApiResp<WxMaUserInfo> userInfo = WxMaHelper.getWxMaUserInfo(evt.getSessionKey(), evt.getEncryptedData(), evt.getIvStr());
        AssertThrow.assertTrue("用户信息解密失败", userInfo.isSuccess());
        BeanUtils.copyProperties(userInfo.getData(), wxUserInfo);
        return RestfulApiRespFactory.ok(wxUserInfo);
    }

    @Override
    public ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq evt) {
        ApiResp<WxMaPhoneNumberInfo> phoneNoInfo = WxMaHelper.getWxMaPhoneNoInfo(evt.getSessionKey(), evt.getEncryptedData(), evt.getIvStr());
        AssertThrow.assertTrue("用户手机号解密失败", phoneNoInfo.isSuccess());
        return RestfulApiRespFactory.ok(phoneNoInfo.getData().getPhoneNumber());
    }

    @Override
    public ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq evt) {

        ApiResp<WxMaJscode2SessionResult> sessionResult = WxMaHelper.getWxMaSessionInfo(evt.getCode());
        AssertThrow.assertTrue("获取微信会活失败", sessionResult.isSuccess());
        WxSessionInfo wxSessionInfo = new WxSessionInfo();
        BeanUtils.copyProperties(sessionResult.getData(), wxSessionInfo);
        return RestfulApiRespFactory.ok(wxSessionInfo);
    }

}
