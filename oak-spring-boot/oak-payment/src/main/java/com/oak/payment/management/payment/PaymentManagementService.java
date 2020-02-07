package com.oak.payment.management.payment;

import com.oak.payment.management.req.CreateOrderReq;
import com.oak.payment.management.req.WechatJsPaymentPreOrderReq;
import com.oak.payment.management.rsp.WechatJsPaymentPreOrderRsp;
import com.wuxp.api.ApiResp;
import com.wuxp.payment.resp.QueryOrderResponse;

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
     * 微信小程序/公众号预下单
     * @param req
     * @return
     */
    ApiResp<WechatJsPaymentPreOrderRsp> wechatJsPaymentPreOrder(WechatJsPaymentPreOrderReq req);
    /**
     * 支付完成
     * @param orderResponse
     * @return
     */
    ApiResp<Void> paymentDone(QueryOrderResponse orderResponse);


}
