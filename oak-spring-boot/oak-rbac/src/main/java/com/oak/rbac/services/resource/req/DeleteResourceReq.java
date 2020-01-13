package com.oak.rbac.services.resource.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "删除资源")
public class DeleteResourceReq extends ApiBaseReq {

    @Schema(description = "资源标识")
    private String id;

    @Schema(description = "模块代码")
    private String moduleCode;


}
