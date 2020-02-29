package com.oak.organization.services.staff.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.organization.enums.StaffAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建Staff
 *  2020-1-19 14:23:00
 */
@Schema(description = "创建CreateStaffReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateStaffReq extends ApiBaseReq {

    @Schema(description = "用户名称")
    @NotNull
    @Size(max = 32)
    private String userName;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "组织id")
    @NotNull
    private Long organizationId;

    @Schema(description = "组织编码")
    @NotNull
    @Size(max = 20)
    private String organizationCode;

    @Schema(description = "员工头像")
    @Size(max = 128)
    private String avatarUrl;

    @Schema(description = "员工手机号")
    @Size(max = 12)
    private String mobilePhone;

    @Schema(description = "账号类型")
    @NotNull
    private StaffAccountType accountType;

    @Schema(description = "创建者")
    private Long creatorId;

    @Schema(description = "关联的账号")
    @NotNull
    private Long adminId;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
