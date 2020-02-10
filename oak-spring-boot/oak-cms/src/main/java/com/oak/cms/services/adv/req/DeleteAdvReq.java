package com.oak.cms.services.adv.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;
import com.oaknt.ncms.enums.AdvCheckState;

/**
 *  删除广告信息
 *  2020-2-10 18:55:01
 */
@Schema(description = "删除广告信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteAdvReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteAdvReq() {
    }

    public DeleteAdvReq(Long id) {
        this.id = id;
    }

}
