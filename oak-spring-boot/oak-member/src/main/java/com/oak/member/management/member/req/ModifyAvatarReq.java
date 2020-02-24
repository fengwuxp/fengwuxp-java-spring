package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author laiy
 * create at 2020-02-24 14:46
 * @Schemaription
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModifyAvatarReq extends ApiBaseReq {

    @Schema(description = "会员ID", hidden = true)
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR)
    private Long uid;

    @Schema(description = "头像URL")
    private String url;
}
