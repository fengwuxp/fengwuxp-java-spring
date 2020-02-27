package com.oak.organization.management.user.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 查询管理员用户
 * 2020-1-16 18:28:37
 */
@Schema(description = "查询管理员用户")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class LoginAdminUserReq extends ApiBaseReq {

    @Schema(description = "用户名")
    @NotNull
    private String userName;

    @Schema(description = "密码")
    @NotNull
    private String password;

}
