package com.oak.api.services.infoprovide.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "创建客户端渠道")
public class CreateClientChannelReq extends ApiBaseReq {

    @Schema(description =  "渠道编号")
    @NotNull
    private String code;

    @Schema(description =  "渠道名称")
    @NotNull
    private String name;

    @Schema(description =  "客户端类型")
    private ClientType clientType;

    @Schema(description =  "排序")
    private Integer orderIndex;

    @Schema(description =  "是否启用")
    private Boolean enabled = true;


}
