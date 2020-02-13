package com.oak.cms.services.advposition.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询广告位信息
 * 2020-2-12 18:54:50
 *
 * @author chenPC
 */
@Schema(description = "查询广告位信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryAdvPositionReq extends ApiBaseQueryReq {

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

    @Schema(description = "最小创建日期")
    @Gte("crateTime")
    private Date minCrateTime;

    @Schema(description = "最大创建日期")
    @Lte("crateTime")
    private Date maxCrateTime;

    @Schema(description = "最小更新日期")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大更新日期")
    @Lte("updateTime")
    private Date maxUpdateTime;

    public QueryAdvPositionReq() {
    }

    public QueryAdvPositionReq(Long id) {
        this.id = id;
    }
}
