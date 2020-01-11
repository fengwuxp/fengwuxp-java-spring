package com.oak.api.services.system.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Schema(description = "查询设置分组")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuerySettingGroupReq extends ApiBaseQueryReq {

    @Schema(description = "分组名称")
    private String name;

    @Schema(description = "是否显示")
    private Boolean show;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "最小更新时间")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("updateTime")
    private Date maxUpdateTime;

    public QuerySettingGroupReq(String name) {
        this.name = name;
    }
}
