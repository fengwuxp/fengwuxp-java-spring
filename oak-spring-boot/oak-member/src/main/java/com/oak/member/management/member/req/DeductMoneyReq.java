package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author: zhuox
 * @create: 2020-02-18
 * @description: 扣减余额请求
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DeductMoneyReq extends ApiBaseReq {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "扣减金额(单位：分)")
    private Integer amount;

    @Schema(description = "订单流水号")
    private String orderSn;

    @Schema(description = "扣除原因")
    private String reason;
}
