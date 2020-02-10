package com.oak.cms.services.adv.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oaknt.ncms.enums.AdvCheckState;
import java.util.Date;
/**
 *  查询广告信息
 *  2020-2-10 18:55:01
 */
@Schema(description = "查询广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryAdvReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告位id")
    private Long apId;

    @Schema(description = "加载广告位")
    @Fetch(value = "advPosition", condition = "#_val==true")
    private Boolean loadAdvPosition;

    @Schema(description = "广告内容描述")
    private String title;

    @Schema(description = "广告内容")
    private String content;

    @Schema(description = "广告URL")
    private String url;

    @Schema(description = "最小广告开始时间")
    @Gte("startDate")
    private Date minStartDate;

    @Schema(description = "最大广告开始时间")
    @Lte("startDate")
    private Date maxStartDate;

    @Schema(description = "最小广告结束时间")
    @Gte("endDate")
    private Date minEndDate;

    @Schema(description = "最大广告结束时间")
    @Lte("endDate")
    private Date maxEndDate;

    @Schema(description = "幻灯片排序")
    private Integer slideSort;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Schema(description = "会员用户名")
    private String memberName;

    @Schema(description = "广告点击率")
    private Integer clickNum;

    @Schema(description = "会员购买的广告是否通过审核")
    private AdvCheckState state;

    @Schema(description = "购买方式")
    private String buyStyle;

    @Schema(description = "购买所支付的金币")
    private Integer goldpay;

    @Schema(description = "归属地区id")
    private String areaId;

    @Schema(description = "加载广告投放地区")
    @Fetch(value = "area", condition = "#_val==true")
    private Boolean loadArea;

    @Schema(description = "是否启用")
    private Boolean enabled;

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

    public QueryAdvReq() {
    }

    public QueryAdvReq(Long id) {
        this.id = id;
    }
}
