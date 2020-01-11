package com.oak.api.services.app.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "find接入账号")
public class FindAuthReq extends ApiBaseReq {

//    @Schema(name = "fromCache", description = "是否使用缓存")
//    protected Boolean fromCache = true;

    @Schema(name = "appId", description = "appId")
    @NotNull
    private String appId;

}
