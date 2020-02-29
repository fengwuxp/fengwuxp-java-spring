package com.oak.organization.services.organizationextendedinfo.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除组织扩展信息
 *  2020-2-2 15:59:04
 */
@Schema(description = "删除组织扩展信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteOrganizationExtendedInfoReq extends ApiBaseReq {

    @Schema(description = "机构ID")
    private Long id;

    @Schema(description = "机构ID集合")
    @In("id")
    private Long[] ids;

    public DeleteOrganizationExtendedInfoReq() {
    }

    public DeleteOrganizationExtendedInfoReq(Long id) {
        this.id = id;
    }

}
