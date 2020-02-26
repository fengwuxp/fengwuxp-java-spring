package com.oak.organization.management.user.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: zhuox
 * @create: 2020-02-04
 * @description: 登录返回信息
 **/
@Schema(description = "登录返回信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class LoginAdminUserInfo {

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "token失效时间")
    private Date tokenExpired;
}
