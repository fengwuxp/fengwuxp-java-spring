package com.oak.api.services.system.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "创建设置")
@Data
public class CreateSettingReq extends ApiBaseReq {


    @Schema(description ="名称")
    @NotNull
    private String name;

    @Schema(description ="配置值")
    @NotNull
    private String value;

    @Schema(description ="配置描述")
    private String description;
}
