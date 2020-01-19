package com.oak.organization.services.organization.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.organization.enums.ApprovalStatus;

import java.util.Date;

/**
 * 查询组织
 * 2020-1-19 13:18:21
 */
@Schema(description = "查询组织")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryOrganizationReq extends ApiBaseQueryReq {

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

    @Schema(description = "最小最后到期日期")
    @Gte("lastAuthEndDate")
    private Date minLastAuthEndDate;

    @Schema(description = "最大最后到期日期")
    @Lte("lastAuthEndDate")
    private Date maxLastAuthEndDate;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "机构拼音首字母")
    private String pinyinInitials;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "加载上级组织")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "ID路径")
    @Like(value = "idPath")
    private String likeIdPath;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "是否允许")
    private Boolean enable;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;


    public QueryOrganizationReq() {
    }

    public QueryOrganizationReq(Long id) {
        this.id = id;
    }
}
