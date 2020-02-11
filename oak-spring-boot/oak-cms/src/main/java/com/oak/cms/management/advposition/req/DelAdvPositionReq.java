package com.oak.cms.management.advposition.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "删除广告位")

public class DelAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    private Long id;

    @Schema(description = "广告位置id集合")
    @In("id")
    private Long[] ids;


}
