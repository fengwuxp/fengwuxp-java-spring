package com.oak.rbac.services.resource.req;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.rbac.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询资源")
public class QueryResourceReq extends ApiBaseQueryReq {

    @Schema(description = "资源标识")
    private String id;

    @Schema(description = "资源类型")
    private ResourceType type;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "模块代码")
    private String moduleCode;


}
