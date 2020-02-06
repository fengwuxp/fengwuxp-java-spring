package com.oak.payment.services.paymentorder;

import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;
import com.oak.payment.services.paymentorder.req.CreatePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.DeletePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.EditPaymentOrderReq;
import com.oak.payment.services.paymentorder.req.QueryPaymentOrderReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  支付订单对象服务
 *  2020-2-6 11:31:05
 */
public interface PaymentOrderService {


    ApiResp<String> create(CreatePaymentOrderReq req);


    ApiResp<Void> edit(EditPaymentOrderReq req);


    ApiResp<Void> delete(DeletePaymentOrderReq req);


    PaymentOrderInfo findById(String sn);


    Pagination<PaymentOrderInfo> query(QueryPaymentOrderReq req);

}
