package com.oak.cms.services.advposition.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;

import com.oak.cms.enums.AdvType;
import com.oak.cms.enums.AdvDisplayType;


/**
 *  查找广告位信息
 *  2020-2-12 18:54:50
 */
@Schema(description = "查找广告位信息")
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