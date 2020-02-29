package com.oak.organization.services.department.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询部门
 *  2020-1-19 14:02:11
 */
@Schema(description = "查询部门")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryDepartmentReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "加载上级部门")
    @Fetch(value = "department", condition = "#_val==true")
    private Boolean loadDepartment;

    @Schema(description = "组织id")
    private Long organizationId;

    @Schema(description = "加载归属组织")
    @Fetch(value = "organization", condition = "#_val==true")
    private Boolean loadOrganization;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "加载上级部门")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;

    @Schema(description = "ID路径")
    private String idPath;

    @Schema(description = "是否删除")
    private Boolean deleted;

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

    public QueryDepartmentReq() {
    }

    public QueryDepartmentReq(Long id) {
        this.id = id;
    }
}
