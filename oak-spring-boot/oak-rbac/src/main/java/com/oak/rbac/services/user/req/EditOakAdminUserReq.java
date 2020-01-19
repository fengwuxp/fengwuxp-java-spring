package com.oak.rbac.services.user.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 编辑管理员用户
 * 2020-1-16 18:28:37
 */
@Schema(description = "编辑管理员用户")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOakAdminUserReq extends ApiBaseReq {

    @Schema(description = "管理员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "昵称")
    @UpdateColumn
    private String nickName;

    @Size(max = 12)
    @Schema(description = "手机号码")
    @UpdateColumn
    private String mobilePhone;

    @Size(max = 64)
    @Schema(description = "邮箱")
    @UpdateColumn
    private String email;

    @Schema(description = "名称")
    @UpdateColumn
    private String name;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    @Schema(description = "密码")
    @Size(min = 6)
    @Ignore
    private String password;

    public EditOakAdminUserReq() {
    }

    public EditOakAdminUserReq(Long id) {
        this.id = id;
    }
}
