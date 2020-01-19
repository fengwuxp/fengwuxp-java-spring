package com.oak.organization.services.department.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;
import com.oak.organization.services.organization.info.OrganizationInfo;


import java.io.Serializable;
import java.util.Date;


/**
 * 部门
 * 2020-1-19 14:02:11
 */
@Schema(description = "部门")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"departmentInfo", "organizationInfo", "parentInfo"})
public class DepartmentInfo implements Serializable {

    private static final long serialVersionUID = 16769191278942150L;
    @Schema(description = "ID")
    private Long id;

    @Desc(value = "", code = "department")
    @Schema(description = "上级部门")
    private DepartmentInfo departmentInfo;

    @Schema(description = "组织id")
    private Long organizationId;

    @Desc(value = "", code = "organization")
    @Schema(description = "归属组织")
    private OrganizationInfo organizationInfo;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "父ID")
    private Long parentId;

    @Desc(value = "", code = "parent")
    @Schema(description = "上级部门")
    private DepartmentInfo parentInfo;

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    private String remark;


}
