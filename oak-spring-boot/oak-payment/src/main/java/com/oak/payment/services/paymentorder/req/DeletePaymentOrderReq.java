package com.oak.payment.services.paymentorder.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除支付订单对象
 *  2020-2-6 11:31:05
 */
@Schema(description = "删除支付订单对象")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeletePaymentOrderReq extends ApiBaseReq {

    @Schema(description = "支付单号")
    private String sn;

    @Schema(description = "支付单号集合")
    @In("sn")
    private String[] sns;

    public DeletePaymentOrderReq() {
    }

    public DeletePaymentOrderReq(String sn) {
        this.sn = sn;
    }

}
