package com.oak.payment.services.payment;

import com.oak.payment.services.payment.info.PaymentInfo;
import com.oak.payment.services.payment.req.CreatePaymentReq;
import com.oak.payment.services.payment.req.DeletePaymentReq;
import com.oak.payment.services.payment.req.EditPaymentReq;
import com.oak.payment.services.payment.req.QueryPaymentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;




/**
 *  支付单对象服务
 *  2020-2-6 11:21:50
 */
public interface PaymentService {


    ApiResp<String> create(CreatePaymentReq req);


    ApiResp<Void> edit(EditPaymentReq req);


    ApiResp<Void> delete(DeletePaymentReq req);


    PaymentInfo findById(String sn);


    Pagination<PaymentInfo> query(QueryPaymentReq req);

}
