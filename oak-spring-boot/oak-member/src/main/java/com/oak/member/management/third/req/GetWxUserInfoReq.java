package com.oak.member.management.third.req;

import com.levin.commons.service.domain.Desc;
import com.levin.commons.service.domain.Sign;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 获取微信用户信息
 */
@Data
@ToString(callSuper = true)
public class GetWxUserInfoReq extends ApiBaseReq {

    @Schema(description = "调用凭证")
    @NotNull
    private String code;
}
