package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询会员登录的token信息
 *  2020-2-18 16:22:54
 */
@Schema(description = "查询会员登录的token信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberTokenReq extends ApiBaseQueryReq {

    @Schema(description = "会员id")
    private Long id;

    @Schema(description = "登录令牌")
    private String token;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "最小登录时间")
    @Gte("loginTime")
    private Date minLoginTime;

    @Schema(description = "最大登录时间")
    @Lte("loginTime")
    private Date maxLoginTime;

    @Schema(description = "最小token到期日期")
    @Gte("expirationDate")
    private Date minExpirationDate;

    @Schema(description = "最大token到期日期")
    @Lte("expirationDate")
    private Date maxExpirationDate;

    @Schema(description = "最小刷新token到期日期")
    @Gte("refreshExpirationDate")
    private Date minRefreshExpirationDate;

    @Schema(description = "最大刷新token到期日期")
    @Lte("refreshExpirationDate")
    private Date maxRefreshExpirationDate;

    public QueryMemberTokenReq() {
    }

    public QueryMemberTokenReq(Long id) {
        this.id = id;
    }
}
