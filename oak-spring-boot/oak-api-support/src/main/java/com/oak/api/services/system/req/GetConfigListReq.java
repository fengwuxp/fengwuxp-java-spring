package com.oak.api.services.system.req;



import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description ="获取配置项值")
public class GetConfigListReq extends ApiBaseReq {

    @Schema(description ="配置名称")
    private String[] names;

    @Schema(description ="是否从缓存加载")
    private Boolean fromCache = true;

}
