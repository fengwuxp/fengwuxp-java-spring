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

    @Schema(description = "id")
    private Long id;

    @Schema(description = "审核状态")
    private ApprovalStatus status;

    @Schema(description = "联系人")
    @Like
    private String contacts;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "名称")
    @Like
    private String name;

}
