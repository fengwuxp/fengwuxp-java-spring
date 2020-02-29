package com.oak.organization.services.organization.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.enums.OrganizationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建Organization
 * 2020-1-19 13:18:21
 */
@Schema(description = "创建CreateOrganizationReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOrganizationReq extends ApiBaseReq {

    @Schema(description = "编号")
    @Size(max = 32)
    private String code;

    @Schema(description = "审核状态")
    private ApprovalStatus status = ApprovalStatus.WAIT;

    @Schema(description = "联系人")
    @Size(max = 50)
    private String contacts;

    @Schema(description = "联系电话")
    @Size(max = 50)
    private String contactMobilePhone;

    @Schema(description = "LOGO")
    private String logo;

    @Schema(description = "区域ID")
    @Size(max = 20)
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "最后到期日期")
    private Date lastAuthEndDate;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "类型")
    @NotNull
    private OrganizationType organizationType;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Schema(description = "备注")
    @Size(max = 1000)
    private String remark;

}
