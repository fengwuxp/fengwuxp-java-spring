package com.oak.payment.services.payment.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除支付单对象
 *  2020-2-6 11:21:49
 */
@Schema(description = "删除支付单对象")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeletePaymentReq extends ApiBaseReq {

    @Schema(description = "支付交易流水号")
    private String sn;

    @Schema(description = "支付交易流水号集合")
    @In("sn")
    private String[] sns;

    public DeletePaymentReq() {
    }

    public DeletePaymentReq(String sn) {
        this.sn = sn;
    }

}
