package com.oak.member.management.member;

import com.oak.member.management.member.info.AccountInfo;
import com.oak.member.management.member.info.CheckMobilePhoneAndOpenIdInfo;
import com.oak.member.management.member.info.MemberLoginInfo;
import com.oak.member.management.member.req.*;
import com.oak.member.services.token.info.MemberTokenInfo;
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

    /**
     * 从微信公众号注册帐号
     *
     * @param req
     * @return
     */
    ApiResp<Long> registerFromWx(RegisterMemberFromWxReq req);

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

    /**
     * 用户登录
     * @param req
     * @return
     */
    ApiResp<MemberLoginInfo> login(MemberLoginReq req);

    /**
     * 充值余额
     * @param req
     * @return
     */
    ApiResp<Void> recharge(RechargeReq req);

    /**
     * 密码验证
     */
    ApiResp checkPassword(CheckPasswordReq req);


    /**
     * 扣除余额
     * @param req
     * @return
     */
    ApiResp<Void> deductMoney(DeductMoneyReq req);

    /**
     * 冻结余额
     * @param req
     * @return
     */
    ApiResp<Void> freezeMoney(FreezeMoneyReq req);

    /**
     * 解冻余额
     * @param req
     * @return
     */
    ApiResp<Void> unfreezeMoney(UnfreezeMoneyReq req);

    /**
     * 检查手机号和微信OPENID
     */
    ApiResp<CheckMobilePhoneAndOpenIdInfo> checkMobilePhoneAndOpenIdWxMa(CheckMobilePhoneAndOpenIdWxMaReq req);

    /**
     * 刷新用户Token
     */
    ApiResp<MemberTokenInfo> refreshMemberToken(RefreshMemberTokenReq req);
}
