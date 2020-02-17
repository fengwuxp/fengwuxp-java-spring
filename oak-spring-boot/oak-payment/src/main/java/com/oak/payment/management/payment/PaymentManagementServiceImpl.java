package com.oak.payment.management.payment;

import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.TradeStatus;
import com.oak.payment.management.payment.req.CreateOrderReq;
import com.oak.payment.management.payment.req.OrderRefundDoneReq;
import com.oak.payment.management.payment.req.PaymentDoneReq;
import com.oak.payment.services.payment.PaymentService;
import com.oak.payment.services.payment.info.PaymentInfo;
import com.oak.payment.services.payment.req.CreatePaymentReq;
import com.oak.payment.services.payment.req.EditPaymentReq;
import com.oak.payment.services.paymentorder.PaymentOrderService;
import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;
import com.oak.payment.services.paymentorder.req.CreatePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.EditPaymentOrderReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: zhuox
 * @create: 2020-02-06
 * @description: 支付单服务实现
 **/
@Slf4j
@Service
public class PaymentManagementServiceImpl implements PaymentManagementService {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentOrderService paymentOrderService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<String> createPaymentOrder(CreateOrderReq req) {
        //支付定单入库
        CreatePaymentOrderReq createPaymentOrderReq = new CreatePaymentOrderReq();
        createPaymentOrderReq.setAmount(req.getAmount())
                .setBuyerId(req.getBuyerId())
                .setSellerId(req.getSellerId())
                .setOrderTypes(req.getOrderTypes())
                .setStatus(PaymentStatus.UNPAID)
                .setType(req.getType())
                .setPaidAmount(0);

        ApiResp<String> apiResp = paymentOrderService.create(createPaymentOrderReq);
        String sn = apiResp.getData();
        //支付单入库
        CreatePaymentReq createPaymentReq = new CreatePaymentReq();
        createPaymentReq.setPayOrderSn(sn)
                .setType(req.getType())
                .setAmount(req.getAmount())
                .setStatus(PaymentStatus.UNPAID);

        return paymentService.create(createPaymentReq);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<String> paymentDone(PaymentDoneReq req) {
        PaymentInfo paymentInfo = paymentService.findById(req.getTradeNo());
        if (paymentInfo == null) {
            return RestfulApiRespFactory.error("支付单不存在");
        }
        if (paymentInfo.getStatus().equals(PaymentStatus.PAID)) {
            return RestfulApiRespFactory.error("订单已支付");
        }
        //修改支付单信息
        EditPaymentReq editPaymentReq = new EditPaymentReq(paymentInfo.getSn());
        editPaymentReq.setPaymentMethodName(req.getPaymentMethodName())
                .setPaymentMethod(req.getPaymentMethod())
                .setPayerAccount(req.getPayerAccount())
                .setReturnSn(req.getOutTradeNo())
                .setFinishedTime(new Date())
                .setStatus(this.transformPaymentStatus(req.getTradeStatus()))
                .setReturnCode(req.getReturnCode())
                .setReturnInfo(req.getReturnInfo());
        paymentService.edit(editPaymentReq);
        //修改支付订单
        PaymentOrderInfo paymentOrderInfo = paymentOrderService.findById(paymentInfo.getPayOrderSn());
        EditPaymentOrderReq editPaymentOrderReq = new EditPaymentOrderReq(paymentOrderInfo.getSn());
        editPaymentOrderReq.setPaidAmount(req.getBuyerPayAmount());
        editPaymentOrderReq.setStatus(editPaymentReq.getStatus());
        paymentOrderService.edit(editPaymentOrderReq);
        return RestfulApiRespFactory.ok(paymentInfo.getPayOrderSn());
    }


    @Override
    public ApiResp<Void> orderRefundDone(OrderRefundDoneReq req) {
        PaymentInfo paymentInfo = paymentService.findById(req.getTradeNo());
        if (paymentInfo == null) {
            return RestfulApiRespFactory.error("支付单不存在");
        }
        if (paymentInfo.getStatus().equals(PaymentStatus.REFUNDED)
                || paymentInfo.getStatus().equals(PaymentStatus.PARTIAL_REFUNDS)) {
            return RestfulApiRespFactory.error("订单已退款");
        }
        //修改支付单信息
        EditPaymentReq editPaymentReq = new EditPaymentReq(paymentInfo.getSn());
        editPaymentReq.setStatus(this.transformPaymentStatus(req.getTradeStatus()))
                .setRefundAmount(req.getRefundAmount())
                .setOutRefundSn(req.getOutTradeRefundNo())
                .setRefundSn(req.getTradeRefundNo())
                .setReturnCode(req.getReturnCode())
                .setReturnInfo(req.getReturnInfo());
        paymentService.edit(editPaymentReq);
        //修改支付订单
        PaymentOrderInfo paymentOrderInfo = paymentOrderService.findById(paymentInfo.getPayOrderSn());
        EditPaymentOrderReq editPaymentOrderReq = new EditPaymentOrderReq(paymentOrderInfo.getSn());
        editPaymentOrderReq.setStatus(editPaymentReq.getStatus());
        paymentOrderService.edit(editPaymentOrderReq);
        return RestfulApiRespFactory.ok();
    }

    private PaymentStatus transformPaymentStatus(String tradeStatus) {
        if (tradeStatus.equals(TradeStatus.SUCCESS.name())) {
            return PaymentStatus.PAID;
        }
        if (tradeStatus.equals(TradeStatus.CLOSED.name())) {
            return PaymentStatus.CANCEL;
        }
        if (tradeStatus.equals(TradeStatus.WAIT_PAY.name())) {
            return PaymentStatus.UNPAID;
        }
        if (tradeStatus.equals(TradeStatus.FAILURE.name())) {
            return PaymentStatus.FAILURE;
        }
        if (tradeStatus.equals(TradeStatus.UNKNOWN.name())) {
            return PaymentStatus.UNPAID;
        }
        if (tradeStatus.equals(TradeStatus.WAIT_REFUND.name())) {
            return PaymentStatus.UNPAID;
        }
        if (tradeStatus.equals(TradeStatus.PARTIAL_REFUND.name())) {
            return PaymentStatus.PARTIAL_REFUNDS;
        }
        if (tradeStatus.equals(TradeStatus.REFUNDED.name())) {
            return PaymentStatus.REFUNDED;
        }
        if (tradeStatus.equals(TradeStatus.NOT_PAY.name())) {
            return PaymentStatus.UNPAID;
        }
        if (tradeStatus.equals(TradeStatus.REFUND_FAILURE.name())) {
            return PaymentStatus.UNPAID;
        }
        return PaymentStatus.CANCEL;
    }
}
