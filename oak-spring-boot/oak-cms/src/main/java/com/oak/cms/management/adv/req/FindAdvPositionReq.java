package com.oak.cms.management.adv.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * 查找广告位信息
 * 2020-2-6 16:50:22
 *
 * @author chenPC
 */
@Schema(description = "查找广告位")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    @NotNull
    @Eq(require = true)
    private Long id;

}
