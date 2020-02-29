package com.oak.rbac.services.user.info;

import com.levin.commons.service.domain.Desc;
import com.oak.rbac.services.role.info.RoleInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * 管理员用户
 * 2020-1-16 18:28:37
 */
@Schema(description = "管理员用户")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class OakAdminUserInfo implements Serializable {

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

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用于密码加密的盐")
    private String cryptoSalt;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "创建人员")
    private String creatorId;

    @Schema(description = "创建人员名称")
    private String creatorName;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "是否可编辑")
    private Boolean editable;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "登录token")
    private String token;

    @Schema(description = "token失效时间")
    private Date tokenExpired;

    @Schema(description = "账号锁定到期时间")
    private Date lockExpired;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "关联的角色列表")
    @Desc(value = "", code = "roles")
    private Set<RoleInfo> roles;
}
