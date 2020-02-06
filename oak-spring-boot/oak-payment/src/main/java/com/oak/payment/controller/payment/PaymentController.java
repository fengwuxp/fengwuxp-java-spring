package com.oak.payment.controller.payment;

import com.oak.payment.services.payment.PaymentService;
import com.oak.payment.services.payment.info.PaymentInfo;
import com.oak.payment.services.payment.req.CreatePaymentReq;
import com.oak.payment.services.payment.req.DeletePaymentReq;
import com.oak.payment.services.payment.req.EditPaymentReq;
import com.oak.payment.services.payment.req.QueryPaymentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Payment")
@Tag(name = "支付单对象", description = "支付单对象管理")
@Slf4j
public class PaymentController {


    @Autowired
    private PaymentService paymentService;



    /**
     * 分页数据
     *
     * @param req  QueryPaymentReq
     * @return  ApiResp<Pagination<PaymentInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询Payment", description = "支付单对象")
    public ApiResp<Pagination<PaymentInfo>> query(QueryPaymentReq req) {
        return RestfulApiRespFactory.ok(paymentService.query(req));
    }




    /**
     * 新增保存
     *
     * @param req   CreatePaymentEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建Payment", description = "支付单对象")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<String> create(CreatePaymentReq req) {
        return paymentService.create(req);
    }



    /**
    * 详情
    *
    * @param sn String
    */
    @GetMapping("/{id}")
    @Operation(summary = "详情Payment", description = "支付单对象")
    public ApiResp<PaymentInfo> detail(@PathVariable String sn) {
        return RestfulApiRespFactory.ok(paymentService.findById(sn));
     }


    /**
     * 修改保存
     */
     @PutMapping("/edit")
     @Operation(summary = "编辑Payment", description = "支付单对象")
     public ApiResp<Void> edit(EditPaymentReq req) {
         return paymentService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除Payment", description = "支付单对象")
    public ApiResp<Void> delete(DeletePaymentReq req) {
        return paymentService.delete(req);
    }


}
