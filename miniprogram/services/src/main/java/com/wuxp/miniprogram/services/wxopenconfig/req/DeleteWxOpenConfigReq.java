package com.wuxp.miniprogram.services.wxopenconfig.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;

/**
 *  删除组织
 *  2020-3-2 14:28:21
 */
@Schema(description = "删除组织")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteWxOpenConfigReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteWxOpenConfigReq() {
    }

    public DeleteWxOpenConfigReq(Long id) {
        this.id = id;
    }

}
