package com.wuxp.security.example.services.user.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Like;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.rbac.entities.E_OakAdminUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 查询管理员用户
 * 2020-1-16 18:28:37
 */
@Schema(description = "查询管理员用户")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class LoginAdminUserReq extends ApiBaseQueryReq {

    @Schema(description = "用户名")
    @NotNull
    private String userName;

    @Schema(description = "密码")
    @NotNull
    private String password;

}
