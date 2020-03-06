package com.wuxp.miniprogram.services.organizationminiprogramconfig.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;

/**
 *  删除组织小程序发布配置
 *  2020-3-2 17:28:20
 */
@Schema(description = "删除组织小程序发布配置")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteOrganizationMiniProgramConfigReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteOrganizationMiniProgramConfigReq() {
    }

    public DeleteOrganizationMiniProgramConfigReq(Long id) {
        this.id = id;
    }

}
