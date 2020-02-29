package com.oak.payment.management.payment;

import com.oak.payment.management.payment.req.CreateImmediateOrderReq;
import com.oak.payment.management.payment.req.CreateOrderReq;
import com.oak.payment.management.payment.req.OrderRefundDoneReq;
import com.oak.payment.management.payment.req.PaymentDoneReq;
import com.oak.payment.services.payment.info.PaymentInfo;
import com.wuxp.api.ApiResp;

/**
* @author: zhuox
* @create: 2020-02-06
* @description: 支付单服务
**/
public interface PaymentManagementService {


    /**
     * 生成支付单
     * @param req
     * @return 支付订单对象SN编号
     */
    ApiResp<PaymentInfo> createPaymentOrder(CreateOrderReq req);

    /**
     * 支付完成
     * @param req
     * @return
     */
    ApiResp<String> paymentDone(PaymentDoneReq req);

    /**
     * 退款完成
     * @param req
     * @return
     */
    ApiResp<String> orderRefundDone(OrderRefundDoneReq req);

    /**
     * 生成支付单，完成支付
     * @param req
     * @return
     */
    ApiResp<String> createImmediatePaymentOrder(CreateImmediateOrderReq req);
}
