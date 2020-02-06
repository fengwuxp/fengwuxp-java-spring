package com.oak.payment.services.payment.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;

import com.oak.payment.enums.PaymentType;
import com.oak.payment.enums.PaymentStatus;


/**
 *  查找支付单对象
 *  2020-2-6 11:21:49
 */
@Schema(description = "查找支付单对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class FindPaymentReq extends ApiBaseReq {

    @Schema(description = "支付交易流水号")
    @NotNull
    @Eq(require = true)
    private String sn;

}
