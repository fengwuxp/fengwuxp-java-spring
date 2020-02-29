package com.oak.organization.services.staff.info;

import com.levin.commons.service.domain.Desc;
import com.oak.organization.enums.StaffAccountType;
import com.oak.organization.services.department.info.DepartmentInfo;
import com.oak.organization.services.organization.info.OrganizationInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 员工
 * 2020-1-19 14:23:00
 */
@Schema(description = "员工")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"departmentInfo", "organizationInfo",})
public class StaffInfo implements Serializable {

    private static final long serialVersionUID = 4839914912652024857L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Desc(value = "", code = "department")
    @Schema(description = "部门")
    private DepartmentInfo departmentInfo;

    @Schema(description = "组织id")
    private Long organizationId;

    @Desc(value = "", code = "organization")
    @Schema(description = "归属组织")
    private OrganizationInfo organizationInfo;

    @Schema(description = "组织编码")
    private String organizationCode;

    @Schema(description = "员工头像")
    private String avatarUrl;

    @Schema(description = "员工手机号")
    private String mobilePhone;

    @Schema(description = "账号类型")
    private StaffAccountType accountType;

    @Schema(description = "创建者")
    private Long creatorId;

    @Schema(description = "关联的账号")
    private Long adminId;

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


}
