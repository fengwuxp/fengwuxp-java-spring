package com.oak.api.services.system.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description ="获取配置项值")
public class GetSettingReq extends ApiBaseReq {

    @Schema(description ="配置名称")
    private String name;

    @Schema(description ="是否从缓存加载")
    private Boolean fromCache = true;

    public GetSettingReq() {
    }

    public GetSettingReq(String name, Boolean fromCache) {
        this.name = name;
        this.fromCache = fromCache;
    }

}
