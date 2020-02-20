package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.UpdateColumn;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员登录的token信息
 *  2020-2-18 16:22:54
 */
@Schema(description = "编辑会员登录的token信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberTokenReq extends ApiBaseReq {

    @Schema(description = "会员id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 512)
    @Schema(description = "登录令牌")
    @UpdateColumn
    private String token;

    @Size(max = 512)
    @Schema(description = "刷新令牌")
    @UpdateColumn
    private String refreshToken;

    @Schema(description = "登录时间")
    @UpdateColumn
    private Date loginTime;

    @Schema(description = "token到期日期")
    @UpdateColumn
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    @UpdateColumn
    private Date refreshExpirationDate;

    public EditMemberTokenReq() {
    }

    public EditMemberTokenReq(Long id) {
        this.id = id;
    }
}
