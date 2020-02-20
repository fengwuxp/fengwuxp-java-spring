package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;



/**
 *  查找会员登录的token信息
 *  2020-2-18 16:22:53
 */
@Schema(description = "查找会员登录的token信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberTokenReq extends ApiBaseReq {

    @Schema(description = "会员id")
    @NotNull
    @Eq(require = true)
    private Long id;

}
