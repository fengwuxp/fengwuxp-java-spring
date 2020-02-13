package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-11 16:05
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MemberAccountInfoReq extends ApiBaseReq {

    @Schema(name = "用户id")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR)
    private Long id;

}
