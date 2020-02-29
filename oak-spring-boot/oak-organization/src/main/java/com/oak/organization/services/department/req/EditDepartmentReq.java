package com.oak.organization.services.department.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑部门
 *  2020-1-19 14:02:11
 */
@Schema(description = "编辑部门")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditDepartmentReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "组织id")
    @UpdateColumn
    private Long organizationId;

    @Schema(description = "层级")
    @UpdateColumn
    private Integer level;

    @Schema(description = "层级排序")
    @UpdateColumn
    private String levelPath;

    @Schema(description = "父ID")
    @UpdateColumn
    private Long parentId;

    @Schema(description = "ID路径")
    @UpdateColumn
    private String idPath;

    @Schema(description = "是否删除")
    @UpdateColumn
    private Boolean deleted;

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

    public EditDepartmentReq() {
    }

    public EditDepartmentReq(Long id) {
        this.id = id;
    }
}
