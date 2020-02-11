package com.oak.member.services.secure.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *  查询MemberSecure
 *  2020-2-6 16:00:48
 */
@Schema(description = "查询MemberSecure")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberSecureReq extends ApiBaseQueryReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "开启二次验证")
    private Boolean totpEnabled;

    @Schema(description = "登录密码")
    private String loginPassword;

    @Schema(description = "登录次数")
    private Integer loginTimes;

    @Schema(description = "最小最后登录时间")
    @Gte("lastLoginTime")
    private Date minLastLoginTime;

    @Schema(description = "最大最后登录时间")
    @Lte("lastLoginTime")
    private Date maxLastLoginTime;

    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    @Schema(description = "最小登录密码设置日期")
    @Gte("loginPwdUpdateTime")
    private Date minLoginPwdUpdateTime;

    @Schema(description = "最大登录密码设置日期")
    @Lte("loginPwdUpdateTime")
    private Date maxLoginPwdUpdateTime;

    @Schema(description = "最小支付密码设置日期")
    @Gte("payPwdUpdateTime")
    private Date minPayPwdUpdateTime;

    @Schema(description = "最大支付密码设置日期")
    @Lte("payPwdUpdateTime")
    private Date maxPayPwdUpdateTime;

    @Schema(description = "需要更改登录密码")
    private Boolean changeLoginPassword;

    @Schema(description = "需要修改支付密码")
    private Boolean changePayPassword;

    @Schema(description = "最小最后登录密码失败时间")
    @Gte("lastLoginFailTime")
    private Date minLastLoginFailTime;

    @Schema(description = "最大最后登录密码失败时间")
    @Lte("lastLoginFailTime")
    private Date maxLastLoginFailTime;

    @Schema(description = "登录密码失败数")
    private Integer loginPwdFail;

    @Schema(description = "最小最后登录密码锁定时间")
    @Gte("lastLoginFailLockTime")
    private Date minLastLoginFailLockTime;

    @Schema(description = "最大最后登录密码锁定时间")
    @Lte("lastLoginFailLockTime")
    private Date maxLastLoginFailLockTime;

    @Schema(description = "最小最后支付密码失败时间")
    @Gte("lastPayFailTime")
    private Date minLastPayFailTime;

    @Schema(description = "最大最后支付密码失败时间")
    @Lte("lastPayFailTime")
    private Date maxLastPayFailTime;

    @Schema(description = "支付密码失败数")
    private Integer payPwdFail;

    @Schema(description = "最小最后支付密码锁定时间")
    @Gte("lastPayFailLockTime")
    private Date minLastPayFailLockTime;

    @Schema(description = "最大最后支付密码锁定时间")
    @Lte("lastPayFailLockTime")
    private Date maxLastPayFailLockTime;

    @Schema(description = "加载会员")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "最小更新日期")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大更新日期")
    @Lte("updateTime")
    private Date maxUpdateTime;

    public QueryMemberSecureReq() {
    }

    public QueryMemberSecureReq(Long id) {
        this.id = id;
    }
}
