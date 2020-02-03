package com.oak.api.services.infoprovide.req;

import com.levin.commons.service.domain.Desc;
import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@Schema(description = "查询客户端渠道")
public class QueryClientChannelReq extends ApiBaseQueryReq {

    @Schema(description = "渠道编号")
    private String code;

    @Schema(description = "渠道名称")
    private String name;

    @Schema(description = "客户端类型")
    private ClientType clientType;

    @Schema(description = "是否启用")
    private Boolean enabled;

    public QueryClientChannelReq(String code) {
        this.code = code;
    }
}
