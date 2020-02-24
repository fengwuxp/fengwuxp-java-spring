package com.oak.member.services.open.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-24 14:55
 * @Schemaription
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ChangePasswordReq extends ApiBaseReq {

    @Schema(description = "会员ID", hidden = true)
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR)
    private Long uid;

    @Schema(description	=	"旧密码")
    @NotNull
    private String oldPassword;

    @Schema(description	=	"新密码")
    @NotNull
    private String newPassword;

}
