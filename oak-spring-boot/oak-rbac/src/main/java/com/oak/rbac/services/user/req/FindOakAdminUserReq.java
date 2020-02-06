package com.oak.rbac.services.user.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;



/**
 *  查找管理员用户
 *  2020-1-16 18:28:37
 */
@Schema(description = "查找管理员用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindOakAdminUserReq extends ApiBaseReq {

    @Schema(description = "管理员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
