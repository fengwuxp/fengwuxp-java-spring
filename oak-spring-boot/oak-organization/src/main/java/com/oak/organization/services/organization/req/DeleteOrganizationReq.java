package com.oak.organization.services.organization.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.oak.organization.enums.ApprovalStatus;

/**
 *  删除组织
 *  2020-1-19 13:18:21
 */
@Schema(description = "删除组织")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteOrganizationReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteOrganizationReq() {
    }

    public DeleteOrganizationReq(Long id) {
        this.id = id;
    }

}
