package com.oak.organization.services.organization.req;

import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import com.oak.organization.enums.ApprovalStatus;


/**
 *  编辑组织
 *  2020-1-19 13:18:21
 */
@Schema(description = "编辑组织")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOrganizationReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "审核状态")
    @UpdateColumn
    private ApprovalStatus status;

    @Size(max = 50)
    @Schema(description = "联系人")
    @UpdateColumn
    private String contacts;

    @Size(max = 50)
    @Schema(description = "联系电话")
    @UpdateColumn
    private String contactMobilePhone;

    @Schema(description = "LOGO")
    @UpdateColumn
    private String logo;

    @Size(max = 20)
    @Schema(description = "区域ID")
    @UpdateColumn
    private String areaId;

    @Schema(description = "区域名称")
    @UpdateColumn
    private String areaName;

    @Schema(description = "详细地址")
    @UpdateColumn
    private String address;

    @Schema(description = "最后到期日期")
    @UpdateColumn
    private Date lastAuthEndDate;

    @Size(max = 64)
    @Schema(description = "机构拼音首字母")
    @UpdateColumn
    private String pinyinInitials;

    @Schema(description = "父ID")
    @UpdateColumn
    private Long parentId;

    @Size(max = 16)
    @Schema(description = "类型")
    @UpdateColumn
    private String type;

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


    @Size(max = 1000)
    @Schema(description = "备注")
    @UpdateColumn
    private String remark;

    public EditOrganizationReq() {
    }

    public EditOrganizationReq(Long id) {
        this.id = id;
    }
}
