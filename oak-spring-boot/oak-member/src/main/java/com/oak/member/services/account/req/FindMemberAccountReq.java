package com.oak.member.services.account.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找会员账户信息
 *  2020-2-6 15:42:46
 */
@Schema(description = "查找会员账户信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberAccountReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

}
