package com.oak.payment.management.payment;

import com.oak.payment.management.payment.req.*;
import com.oak.payment.management.payment.rsp.WechatJsPaymentPreOrderRsp;
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
    ApiResp<String> createPaymentOrder(CreateOrderReq req);

    /**
     * 支付完成
     * @param req
     * @return
     */
    ApiResp<Void> paymentDone(PaymentDoneReq req);

    /**
     * 退款完成
     * @param req
     * @return
     */
    ApiResp<Void> orderRefundDone(OrderRefundDoneReq req);
}
