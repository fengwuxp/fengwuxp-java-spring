package com.oak.payment.controller.paymentorder;

import com.wuxp.api.ApiResp;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.oak.payment.services.paymentorder.PaymentOrderService;
import com.oak.payment.services.paymentorder.req.*;
import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;


@RestController
@RequestMapping("/PaymentOrder")
@Tag(name = "支付订单对象", description = "支付订单对象管理")
@Slf4j
public class PaymentOrderController {


    @Autowired
    private PaymentOrderService paymentOrderService;



    /**
     * 分页数据
     *
     * @param req  QueryPaymentOrderReq
     * @return  ApiResp<Pagination<PaymentOrderInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询PaymentOrder", description = "支付订单对象")
    public ApiResp<Pagination<PaymentOrderInfo>> query(QueryPaymentOrderReq req) {
        return RestfulApiRespFactory.ok(paymentOrderService.query(req));
    }




    /**
     * 新增保存
     *
     * @param req   CreatePaymentOrderEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建PaymentOrder", description = "支付订单对象")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<String> create(CreatePaymentOrderReq req) {
        return paymentOrderService.create(req);
    }



    /**
    * 详情
    *
    * @param sn String
    */
    @GetMapping("/{id}")
    @Operation(summary = "详情PaymentOrder", description = "支付订单对象")
    public ApiResp<PaymentOrderInfo> detail(@PathVariable String sn) {
        return RestfulApiRespFactory.ok(paymentOrderService.findById(sn));
     }


    /**
     * 修改保存
     */
     @PutMapping("/edit")
     @Operation(summary = "编辑PaymentOrder", description = "支付订单对象")
     public ApiResp<Void> edit(EditPaymentOrderReq req) {
         return paymentOrderService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除PaymentOrder", description = "支付订单对象")
    public ApiResp<Void> delete(DeletePaymentOrderReq req) {
        return paymentOrderService.delete(req);
    }


}
