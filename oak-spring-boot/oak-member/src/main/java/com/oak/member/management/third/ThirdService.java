package com.oak.member.management.third;

import com.levin.commons.service.domain.ApiService;
import com.levin.commons.service.domain.Desc;
import com.oak.member.management.third.info.WxSessionInfo;
import com.oak.member.management.third.info.WxUserInfo;
import com.oak.member.management.third.req.*;
import com.wuxp.api.ApiResp;

/**
 * 第三方平台
 * @author laiy
 */
public interface ThirdService {

    @Desc(value = "获取微信UnionID")
    ApiResp<String> getWxUnionID(GetWxUnionIDReq evt);

    @Desc(value = "获取微信用户信息")
    ApiResp<WxUserInfo> getWxUserInfo(GetWxUserInfoReq evt);

    @Desc(value = "获取微信小程序会话信息")
    ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq evt);

    @Desc(value = "获取微信小程序用户信息")
    ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq evt);

    @Desc(value = "获取微信小程序用户手机号")
    ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq evt);

}
