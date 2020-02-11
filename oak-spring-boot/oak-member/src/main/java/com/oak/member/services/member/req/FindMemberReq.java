package com.oak.member.services.member.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找会员信息
 *  2020-2-6 15:32:43
 */
@Schema(description = "查找会员信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
