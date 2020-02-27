package com.oak.member.management.third.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
@Schema(description = "获取微信UnionID")
public class GetWxUnionIDReq extends ApiBaseReq {

    @Schema(description = "调用凭证")
    @NotNull
    private String accessToken;

    @Schema(description = "openid")
    @NotNull
    private String openid;
}
