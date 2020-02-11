package com.oak.cms.management.adv.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 查询广告信息
 *
 * @author chenPC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "查询广告信息")
public class QueryAdvInfoReq extends ApiBaseReq {

    @Schema(description = "广告内容描述")
    private String title;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
