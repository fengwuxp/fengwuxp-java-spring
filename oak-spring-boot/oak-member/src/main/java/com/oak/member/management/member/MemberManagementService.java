package com.oak.member.management.member;

import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.req.MemberAccountInfoReq;
import com.oak.member.management.member.req.RegisterMemberFromWxMaReq;
import com.oak.member.management.member.req.RegisterMemberReq;
import com.wuxp.api.ApiResp;

/**
 * 用户管理
 *
 * @author laiy
 * create at 2020-02-06 16:11
 * @Description
 */
public interface MemberManagementService {



    /**
     * 注册账号
     *
     * @param req
     * @return
     */
    ApiResp<Long> register(RegisterMemberReq req);

    ///**
    // * 从微信公众号注册帐号
    // *
    // * @param req
    // * @return
    // */
    //ApiResp<Long> registerFromWx(RegisterMemberFromWxMaReq req);

    /**
     * 从微信小程序注册帐号
     *
     * @param req
     * @return
     */
    ApiResp<Long> registerFromWxMa(RegisterMemberFromWxMaReq req);

    /**
     * 获取账户信息
     * @return
     */
    ApiResp<AccountInfo> getMemberInfo(MemberAccountInfoReq req);



}
