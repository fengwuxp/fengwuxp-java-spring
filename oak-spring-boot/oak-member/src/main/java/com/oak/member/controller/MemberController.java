package com.oak.member.controller;

import com.levin.commons.dao.JpaDao;
import com.oak.member.management.member.MemberManagementService;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.member.management.member.info.MemberLoginInfo;
import com.oak.member.management.member.req.*;
import com.oak.member.management.third.ThirdService;
import com.oak.member.management.third.info.WxSessionInfo;
import com.oak.member.management.third.info.WxUserInfo;
import com.oak.member.management.third.req.*;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.wuxp.api.ApiResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author laiy
 * create at 2020-02-19 14:56
 * @Description
 */

@RestController
@RequestMapping("/member")
@Slf4j
@Tag(name = "用户服务", description = "用户相关服务")
public class MemberController {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private JpaDao jpaDao;

    /**
     * 用户注册
     *
     * @return
     */
    @GetMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册")
    public ApiResp<Long> register(RegisterMemberReq dto) {
        return memberManagementService.register(dto);
    }

    /**
     * 用户注册 (从微信小程序注册)
     *
     * @return
     */
    @GetMapping("/register_wx_ma")
    @Operation(summary = "用户注册 (从微信小程序注册)", description = "用户注册 (从微信小程序注册)")
    public ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq dto) {
        return memberManagementService.registerFromWxMa(dto);
    }

//    /**
//     * 用户注册 (从微信公众号注册)
//     * @return
//     */
//    @GetMapping("/registerWxMa")
//    @Operation(summary = "用户注册 (从微信公众号注册)", description = "用户注册 (从微信公众号注册)")
//    public ApiResp<Long> registerFromWx(RegisterMemberFromWxReq dto) {
//        return memberManagementService.registerFromWx(dto);
//    }

    /**
     * 获取账户信息
     *
     * @return
     */
    @GetMapping("/account_info")
    @Operation(summary = "获取账户信息)", description = "获取账户信息")
    public ApiResp<AccountInfo> getMemberAccountInfo(MemberAccountInfoReq dto) {
        return memberManagementService.getMemberInfo(dto);
    }


    @GetMapping("/login")
    @Operation(summary = "用户登录)", description = "用户登录")
    public ApiResp<MemberLoginInfo> login(MemberLoginReq req) {
        return memberManagementService.login(req);
    }

    /**
     * 检查手机号和微信OPENID
     */
    @GetMapping("/check_mobile_openid")
    @Operation(summary = "检查手机号和微信OPENID)", description = "检查手机号和微信OPENID")
    public ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req) {
        return memberManagementService.checkMobilePhoneAndOpenIdWxMa(req);
    }

    /**
     * 刷新用户Token
     */
    @GetMapping("/refresh_token")
    @Operation(summary = "刷新用户Token)", description = "刷新用户Token")
    public ApiResp<MemberTokenInfo> refreshMemberToken(RefreshMemberTokenReq req) {
        return memberManagementService.refreshMemberToken(req);
    }

}
