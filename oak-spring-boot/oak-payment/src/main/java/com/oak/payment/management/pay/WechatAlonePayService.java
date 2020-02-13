package com.oak.payment.management.pay;

import com.oak.payment.management.pay.req.WechatJsApiPreOrderReq;
import com.wuxp.payment.resp.PreOrderResponse;
import com.wuxp.payment.wechat.model.WechatJsTradePayResult;

/**
* @author: zhuox
* @create: 2020-02-11
* @description: 微信支付服务
**/
public interface WechatAlonePayService {

    /**
     *
     * @param req
     * @return
     */
   PreOrderResponse<WechatJsTradePayResult> jsApiPreOrder(WechatJsApiPreOrderReq req);

}
