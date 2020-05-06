package com.wuxp.miniprogram.services.wxopenconfig.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 *  查询组织
 *  2020-3-2 14:28:21
 */
@Schema(description = "查询组织")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryWxOpenConfigReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "组织编号")
    private Long organizationId;

    @Schema(description = "组织代码")
    private String organizationCode;

    @Schema(description = "小程序AppId")
    private String miniProgramAppId;

    @Schema(description = "小程序AppSecret")
    private String miniProgramAppSecret;

    @Schema(description = "小程序Token")
    private String miniProgramToken;

    @Schema(description = "小程序审核id")
    private String miniProgramAuditId;

    @Schema(description = "公众号Appid")
    private String officialAccountAppId;

    @Schema(description = "公众号AppSecret")
    private String officialAccountAppSecret;

    public QueryWxOpenConfigReq() {
    }

    public QueryWxOpenConfigReq(Long id) {
        this.id = id;
    }
}
