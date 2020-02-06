package com.oak.cms.services.adv.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;
        import com.oak.cms.enums.AdvType;
        import com.oak.cms.enums.AdvDisplayType;
        import com.oak.api.services.infoprovide.info.AreaInfo;


import java.io.Serializable;
import java.util.Date;


/**
* 广告位信息
* 2020-2-6 16:50:22
*/
@Schema(description ="广告位信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"areaInfo",})
public class AdvPositionInfo implements Serializable {

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

          @Desc(value = "",code = "area")
        @Schema(description = "地区")
        private AreaInfo areaInfo;

        @Schema(description = "广告规格")
        private String spec;

        @Schema(description = "创建日期")
        private Date crateTime;

        @Schema(description = "更新日期")
        private Date updateTime;


}
