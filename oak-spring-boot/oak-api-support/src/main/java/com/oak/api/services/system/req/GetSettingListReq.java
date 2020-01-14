package com.oak.api.services.system.req;



import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@Schema(description ="获取配置项值")
public class GetSettingListReq extends ApiBaseReq {

    @Schema(description ="配置名称")
    @NotNull
    private String[] names;

}
