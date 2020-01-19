package com.oak.organization.services.staff.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.oak.organization.enums.StaffAccountType;


/**
 *  编辑员工
 *  2020-1-19 14:23:00
 */
@Schema(description = "编辑员工")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditStaffReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "用户名称")
    @UpdateColumn
    private String userName;

    @Schema(description = "部门ID")
    @UpdateColumn
    private Long departmentId;

    @Schema(description = "组织id")
    @UpdateColumn
    private Long organizationId;

    @Size(max = 20)
    @Schema(description = "组织编码")
    @UpdateColumn
    private String organizationCode;

    @Size(max = 128)
    @Schema(description = "员工头像")
    @UpdateColumn
    private String avatarUrl;

    @Size(max = 12)
    @Schema(description = "员工手机号")
    @UpdateColumn
    private String mobilePhone;

    @Schema(description = "账号类型")
    @UpdateColumn
    private StaffAccountType accountType;

    @Schema(description = "创建者")
    @UpdateColumn
    private Long creatorId;

    @Schema(description = "关联的账号")
    @UpdateColumn
    private Long adminId;

    @Schema(description = "名称")
    @UpdateColumn
    private String name;

    @Schema(description = "排序代码")
    @UpdateColumn
    private Integer orderCode;

    @Schema(description = "是否允许")
    @UpdateColumn
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @UpdateColumn
    private Boolean editable;

    @Schema(description = "更新时间")
    @UpdateColumn
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditStaffReq() {
    }

    public EditStaffReq(Long id) {
        this.id = id;
    }
}
