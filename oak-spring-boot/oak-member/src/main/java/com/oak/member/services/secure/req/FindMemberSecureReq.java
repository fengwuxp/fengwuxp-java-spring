package com.oak.member.services.secure.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找MemberSecure
 *  2020-2-6 16:00:47
 */
@Schema(description = "查找MemberSecure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindMemberSecureReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
