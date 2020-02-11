package com.oak.member.services.open.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找会员绑定信息
 *  2020-2-8 20:22:08
 */
@Schema(description = "查找会员绑定信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberOpenReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
