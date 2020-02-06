package com.oak.organization.services.department.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建Department
 * 2020-1-19 14:02:11
 *
 * @author
 */
@Schema(description = "创建CreateDepartmentReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateDepartmentReq extends ApiBaseReq {

    @Schema(description = "组织id")
    @NotNull
    private Long organizationId;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
