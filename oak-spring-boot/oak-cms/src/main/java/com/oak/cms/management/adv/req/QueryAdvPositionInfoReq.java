package com.oak.cms.management.adv.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
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
public class QueryAdvPositionInfoReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    private Long id;

    @Schema(description = "引用编码")
    private String code;

    @Schema(description = "广告位置名称")
    private String name;

    @Schema(description = "广告位简介")
    private String description;

    @Schema(description = "广告类别")
    private AdvType type;

    @Schema(description = "广告展示方式")
    private AdvDisplayType displayType;

    @Schema(description = "广告位是否启用")
    private Boolean enabled;

    @Schema(description = "广告位宽度")
    private Integer width;

    @Schema(description = "广告位高度")
    private Integer height;

    @Schema(description = "广告位单价")
    private Integer price;

    @Schema(description = "拥有的广告数")
    private Integer num;

    @Schema(description = "广告位点击率")
    private Integer clickNum;

    @Schema(description = "广告位默认内容")
    private String defaultContent;

    @Schema(description = "归属地区id")
    private String areaId;

    @Schema(description = "广告规格")
    private String spec;
}
