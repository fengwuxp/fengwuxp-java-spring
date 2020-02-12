package com.oak.payment.management.pay;

import com.oak.payment.management.pay.req.WechatJsApiPreOrderReq;
import com.wuxp.payment.DelegatePlatformPaymentService;
import com.wuxp.payment.enums.PaymentMethod;
import com.wuxp.payment.enums.PaymentPlatform;
import com.wuxp.payment.req.PreOrderRequest;
import com.wuxp.payment.resp.PreOrderResponse;
import com.wuxp.payment.springboot.DefaultPlatformPaymentServiceProvider;
import com.wuxp.payment.wechat.WechatJsPaymentService;
import com.wuxp.payment.wechat.model.WechatJsTradePayResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author: zhuox
 * @create: 2020-02-11
 * @description: 微信支付服务实现
 **/
@Slf4j
public class WechatAlonePayServiceImpl implements WechatAlonePayService{

    @Override
    public PreOrderResponse<WechatJsTradePayResult> jsApiPreOrder(WechatJsApiPreOrderReq req) {
        PreOrderRequest preOrderRequest = new PreOrderRequest();
        BeanUtils.copyProperties(req, preOrderRequest);
        WechatJsPaymentService paymentService = new WechatJsPaymentService();
        paymentService.setPaymentConfig(req.getWechatPaymentConfig());
        return paymentService.preOrder(preOrderRequest);
    }

}
