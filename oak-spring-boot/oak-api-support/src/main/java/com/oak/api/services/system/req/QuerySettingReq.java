package com.oak.api.services.system.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "查询系统设置")
@Data
public class QuerySettingReq extends ApiBaseQueryReq {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "名称")
    @In
    private String[] names;

    @Schema(description = "配置值")
    private String value;

    @Schema(description = "配置描述")
    private String description;

    @Schema(description = "分组名称")
    private String groupName;

    @Schema(description = "是否显示")
    private Boolean show;

    @Schema(description = "客户端是否可获取")
    private Boolean open;

    public QuerySettingReq(String name, Boolean fromCache) {
        this.name = name;
        super.setFromCache(fromCache);
    }

    public QuerySettingReq() {
    }

}
