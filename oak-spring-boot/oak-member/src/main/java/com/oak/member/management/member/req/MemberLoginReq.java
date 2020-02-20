package com.oak.member.management.member.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.LoginModel;
import com.oak.member.enums.OpenType;
import com.wuxp.api.context.InjectField;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

import static com.oak.member.constant.MemberApiContextInjectExprConstant.INJECT_REQUEST_IP_EXPR;

/**
 * @author laiy
 * create at 2020-02-17 10:04
 * @Description
 */
@Data
@Desc(value = "会员登录")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberLoginReq extends ApiBaseReq {
    @Desc(value = "登陆模式")
    private LoginModel loginModel = LoginModel.PASSWORD;

    //密码登录-------------------------------------------------------
    @Desc(value = "用户名")
    private String userName;

    @Desc(value = "手机号码")
    private String mobilePhone;

    @Desc(value = "登录密码")
    private String password;

    //开放平台登录---------------------------------------------------------
    @Desc(value = "平台类型")
    private OpenType openType;

    @Desc(value = "OPENID")
    private String openId;

    @Desc(value = "UNIONID，有值优先使用")
    private String unionId;

    //刷新TOKEN------------------------------------------------------------
    @Desc(value = "Token")
    private String token;

    @Desc(value = "登录IP")
    @InjectField(value = INJECT_REQUEST_IP_EXPR)
    private String remoteIp;

}
