package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-21 15:30
 * @Description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryMemberByOpenIdReq extends ApiBaseReq {

    @Schema(name = "openId", description = "OPENID")
    @NotNull
    private String openId;

    @Schema(name = "openType", description = "第三方平台类型")
    @NotNull
    private OpenType openType;

}
