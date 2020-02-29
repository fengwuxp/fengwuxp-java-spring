package com.oak.organization.services.staff.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.organization.enums.StaffAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询员工
 *  2020-1-19 14:23:00
 */
@Schema(description = "查询员工")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryStaffReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "用户名称")
    private String userName;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "加载部门")
    @Fetch(value = "department", condition = "#_val==true")
    private Boolean loadDepartment;

    @Schema(description = "组织id")
    private Long organizationId;

    @Schema(description = "加载归属组织")
    @Fetch(value = "organization", condition = "#_val==true")
    private Boolean loadOrganization;

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

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小更新时间")
    @Gte("lastUpdateTime")
    private Date minLastUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("lastUpdateTime")
    private Date maxLastUpdateTime;

    @Schema(description = "备注")
    private String remark;

    public QueryStaffReq() {
    }

    public QueryStaffReq(Long id) {
        this.id = id;
    }
}
