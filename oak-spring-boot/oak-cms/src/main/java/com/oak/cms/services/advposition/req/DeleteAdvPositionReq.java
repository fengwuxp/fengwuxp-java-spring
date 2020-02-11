package com.oak.cms.services.advposition.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 删除广告位信息
 * 2020-2-6 16:50:22
 *
 * @author chenPC
 */
@Schema(description = "删除广告位信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    private Long id;

    @Schema(description = "广告位置id集合")
    @In("id")
    private Long[] ids;

    public DeleteAdvPositionReq() {
    }

    public DeleteAdvPositionReq(Long id) {
        this.id = id;
    }

}
