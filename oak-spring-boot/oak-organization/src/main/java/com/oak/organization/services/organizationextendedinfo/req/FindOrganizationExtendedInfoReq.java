package com.oak.organization.services.organizationextendedinfo.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;



/**
 *  查找组织扩展信息
 *  2020-2-2 15:59:04
 */
@Schema(description = "查找组织扩展信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindOrganizationExtendedInfoReq extends ApiBaseReq {

    @Schema(description = "机构ID")
    @NotNull
    @Eq(require = true)
    private Long id;

}
