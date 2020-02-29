package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author laiy
 * create at 2020-02-19 15:13
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RefreshMemberTokenReq extends ApiBaseReq {

    @Schema(name = "token", description = "Token")
    private String token;

}
