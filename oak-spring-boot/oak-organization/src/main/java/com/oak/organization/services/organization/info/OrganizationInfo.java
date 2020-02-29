package com.oak.organization.services.organization.info;

import com.levin.commons.service.domain.Desc;
import com.oak.organization.enums.ApprovalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 组织
 * 2020-1-19 13:18:21
 */
@Schema(description = "组织")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parentInfo",})
public class OrganizationInfo implements Serializable {

    private static final long serialVersionUID = 4146251376336548110L;
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "审核状态")
    private ApprovalStatus status;

    @Schema(description = "联系人")
    private String contacts;

    @Schema(description = "联系电话")
    private String contactMobilePhone;

    @Schema(description = "LOGO")
    private String logo;

    @Schema(description = "区域ID")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "最后到期日期")
    private Date lastAuthEndDate;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "层级路径")
    private String levelPath;

    @Schema(description = "机构拼音首字母")
    private String pinyinInitials;

    @Schema(description = "父ID")
    private Long parentId;

    @Desc(value = "", code = "parent")
    @Schema(description = "上级组织")
    private OrganizationInfo parentInfo;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "ID路径")
    private String idPath;

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
