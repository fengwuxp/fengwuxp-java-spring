package com.oak.member.controller;

import com.oak.member.management.third.ThirdService;
import com.oak.member.management.third.info.WxSessionInfo;
import com.oak.member.management.third.info.WxUserInfo;
import com.oak.member.management.third.req.*;
import com.wuxp.api.ApiResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laiy
 * create at 2020-02-19 14:56
 * @Description
 */

@RestController
@
        RequestMapping("/third")
@Slf4j
@Tag(name = "第三方服务", description = "第三方服务")
public class ThirdController {

    @Autowired
    private ThirdService thirdService;

    @GetMapping("/get_wx_union_id")
    @Operation(summary = "获取微信UnionID", description = "获取微信UnionID")
    public ApiResp<String> getWxUnionID(GetWxUnionIDReq evt) {
        return thirdService.getWxUnionID(evt);
    }


    @GetMapping("/get_wx_user_id")
    @Operation(summary = "获取微信用户信息", description = "获取微信用户信息")
    public ApiResp<WxUserInfo> getWxUserInfo(GetWxUserInfoReq evt) {
        return thirdService.getWxUserInfo(evt);
    }


    @GetMapping("/get_wx_session_info")
    @Operation(summary = "获取微信小程序会话信息", description = "获取微信小程序会话信息")
    public ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq evt) {
        return thirdService.getWxMaSessionInfo(evt);
    }


    @GetMapping("/get_wx_ma_user_info")
    @Operation(summary = "获取微信小程序用户信息", description = "获取微信小程序用户信息")
    public ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq evt) {
        return thirdService.getWxMaUserInfo(evt);
    }


    @GetMapping("/get_wx_ma_phone_number")
    @Operation(summary = "获取微信小程序用户手机号", description = "获取微信小程序用户手机号")
    public ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq evt) {
        return thirdService.getWxMaPhoneNumber(evt);
    }

}
