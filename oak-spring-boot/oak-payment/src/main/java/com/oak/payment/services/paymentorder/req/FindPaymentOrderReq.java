package com.oak.payment.services.paymentorder.req;

import com.levin.commons.dao.annotation.Eq;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  查找支付订单对象
 *  2020-2-6 11:31:05
 */
@Schema(description = "查找支付订单对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindPaymentOrderReq extends ApiBaseReq {

    @Schema(description = "支付单号")
    @NotNull
    @Eq(require = true)
    private String sn;

}
