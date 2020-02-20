package com.oak.member.services.token.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建MemberToken
 *  2020-2-18 16:22:53
 */
@Schema(description = "创建CreateMemberTokenReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberTokenReq extends ApiBaseReq {

    @Schema(description = "登录令牌")
    @NotNull
    @Size(max = 512)
    private String token;

    @Schema(description = "刷新令牌")
    @Size(max = 512)
    private String refreshToken;

    @Schema(description = "登录时间")
    @NotNull
    private Date loginTime;

    @Schema(description = "token到期日期")
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    private Date refreshExpirationDate;

}
