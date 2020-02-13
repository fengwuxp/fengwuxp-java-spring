package com.oak.member.services.secure.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  创建MemberSecure
 *  2020-2-6 16:00:47
 */
@Schema(description = "创建CreateMemberSecureReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberSecureReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    private Long id;

    //@Schema(description = "加密key")
    //@NotNull
    //private String pwdKey;
    //
    //@Schema(description = "动态口令安全密钥")
    //@NotNull
    //@Size(max = 16)
    //@Size(min = 16 , max = 16)
    //private String totpSecret;

    @Schema(description = "登录密码")
    @Size(max = 128)
    private String loginPassword;

    @Schema(description = "支付密码")
    @Size(max = 128)
    private String payPassword;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "最后登录IP")
    @Size(max = 16)
    private String lastLoginIp;

    @Schema(description = "登录密码设置日期")
    private Date loginPwdUpdateTime;

    @Schema(description = "支付密码设置日期")
    private Date payPwdUpdateTime;

    @Schema(description = "最后登录密码失败时间")
    private Date lastLoginFailTime;

    @Schema(description = "最后登录密码锁定时间")
    private Date lastLoginFailLockTime;

    @Schema(description = "最后支付密码失败时间")
    private Date lastPayFailTime;

    @Schema(description = "最后支付密码锁定时间")
    private Date lastPayFailLockTime;

}
