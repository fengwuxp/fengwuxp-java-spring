package com.oak.cms.services.adv.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;
        import com.oaknt.ncms.enums.AdvCheckState;
        import com.oak.api.services.infoprovide.info.AreaInfo;


import java.io.Serializable;
import java.util.Date;


/**
* 广告信息
* 2020-2-10 18:55:01
*/
@Schema(description ="广告信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"advPositionInfo","areaInfo",})
public class AdvInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "广告位id")
        private Long apId;

          @Desc(value = "",code = "advPosition")
        @Schema(description = "广告位")
        private AdvPositionInfo advPositionInfo;

        @Schema(description = "广告内容描述")
        private String title;

        @Schema(description = "广告内容")
        private String content;

        @Schema(description = "广告URL")
        private String url;

        @Schema(description = "广告开始时间")
        private Date startDate;

        @Schema(description = "广告结束时间")
        private Date endDate;

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

          @Desc(value = "",code = "area")
        @Schema(description = "广告投放地区")
        private AreaInfo areaInfo;

        @Schema(description = "是否启用")
        private Boolean enabled;

        @Schema(description = "创建日期")
        private Date crateTime;

        @Schema(description = "更新日期")
        private Date updateTime;


}
