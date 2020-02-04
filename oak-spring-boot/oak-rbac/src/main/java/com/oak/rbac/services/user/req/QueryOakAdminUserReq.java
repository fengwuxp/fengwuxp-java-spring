package com.oak.rbac.services.user.req;

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

public class QueryOakAdminUserReq extends ApiBaseQueryReq {

    @Schema(description = "管理员ID")
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "昵称模糊查询")
    @Like(value = E_OakAdminUser.nickName)
    private String likeNickName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "创建人员")
    private String creatorId;

    @Schema(description = "创建人员名称")
    private String creatorName;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "名称模糊查询")
    @Like(value = E_OakAdminUser.name)
    private String likeName;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "token失效时间")
    @Gte("tokenExpired")
    private Date tokenExpired;

    public QueryOakAdminUserReq() {
    }

    public QueryOakAdminUserReq(Long id) {
        this.id = id;
    }
}
