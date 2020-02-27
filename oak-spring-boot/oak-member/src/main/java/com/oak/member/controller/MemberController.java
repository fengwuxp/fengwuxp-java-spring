package com.oak.member.controller;

import com.levin.commons.dao.JpaDao;
import com.oak.member.management.member.MemberManagementService;
import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.member.management.member.info.MemberLoginInfo;
import com.oak.member.management.member.req.*;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.EditMemberReq;
import com.oak.member.services.member.req.QueryMemberReq;
import com.oak.member.services.open.req.ChangePasswordReq;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
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
@RequestMapping("/member")
@Slf4j
@Tag(name = "用户服务", description = "用户相关服务")
public class MemberController {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private MemberService memberService;

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

    @GetMapping("/unilogin")
    @Operation(summary = "统一登录注册)", description = "统一登录注册")
    public ApiResp<MemberLoginInfo> unilogin(UniloginReq req) {
        return memberManagementService.unilogin(req);
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

    /**
     * 刷新用户Token
     */
    @GetMapping("/edit_member")
    @Operation(summary = "修改用户信息)", description = "修改用户信息")
    public ApiResp<Void> editMember(EditMemberReq req) {
        return memberService.edit(req);
    }

    /**
     * 修改用户头像信息
     */
    @GetMapping("/modify_avatar")
    @Operation(summary = "修改用户头像信息)", description = "修改用户头像信息")
    ApiResp<Void> modifyAvatar(ModifyAvatarReq req) {
        return memberManagementService.modifyAvatar(req);
    }

    /**
     * 修改密码
     */
    @GetMapping("/change_password")
    @Operation(summary = "修改密码)", description = "修改密码")
    ApiResp changePassword(ChangePasswordReq req) {
        return memberManagementService.changePassword(req);
    }

    /**
     * 冻结用户
     */
    @GetMapping("/frozen_member")
    @Operation(summary = "冻结用户)", description = "冻结用户")
    ApiResp frozen(FrozenReq req) {
        return memberManagementService.frozen(req);
    }

    /**
     * 搜索用户
     */
    @GetMapping("/query_member")
    @Operation(summary = "搜索用户)", description = "搜索用户")
    ApiResp<Pagination<MemberInfo>> queryMember(QueryMemberReq req) {
        return RestfulApiRespFactory.ok(memberService.query(req));
    }


}
