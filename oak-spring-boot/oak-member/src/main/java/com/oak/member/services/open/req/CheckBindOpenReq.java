package com.oak.member.services.open.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-19 15:53
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)

public class CheckBindOpenReq extends ApiBaseReq {

    @Schema(description = "平台类型")
    @NotNull
    private OpenType openType;

    @Schema(description = "会员ID")
    @NotNull
    private Long memberId;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "unionId")
    private String unionId;

}
