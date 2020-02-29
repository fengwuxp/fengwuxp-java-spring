package test.com.oak.payment.services.paymentorder;

import com.github.javafaker.Faker;
import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import com.oak.payment.services.payment.PaymentService;
import com.oak.payment.services.payment.info.PaymentInfo;
import com.oak.payment.services.payment.req.CreatePaymentReq;
import com.oak.payment.services.payment.req.QueryPaymentReq;
import com.oak.payment.services.paymentorder.PaymentOrderService;
import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;
import com.oak.payment.services.paymentorder.req.CreatePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.QueryPaymentOrderReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.payment.OakApplicationTest;

import java.util.Date;
import java.util.Locale;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
class PaymentOrderServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    private Faker faker = new Faker(new Locale("zh","CN"));

    @Test
    void create() {
        CreatePaymentOrderReq createPaymentOrderReq = new CreatePaymentOrderReq();
        createPaymentOrderReq.setAmount(100)
                .setBuyerId(1L)
                .setSellerId(1L)
                .setOrderTypes("wxpay")
                .setStatus(PaymentStatus.PAID)
                .setType(PaymentType.PAYMENT)
                .setExpirationTime( new Date())
                .setPaidAmount(100);

        ApiResp<String> apiResp = paymentOrderService.create(createPaymentOrderReq);
        String sn = apiResp.getData();

        CreatePaymentReq createPaymentReq = new CreatePaymentReq();
        createPaymentReq.setPayOrderSn(sn)
                .setPaymentMethodName("微信支付")
                .setType(PaymentType.PAYMENT)
                .setPayerName("ZS")
                .setPayerAccount("13275666")
                .setPayerMobilePhone("13275921111")
                .setAmount(100)
                .setPaymentMethod("WXPAY")
                .setPayTime(new Date())
                .setFinishedTime(new Date())
                .setOperator("ZS")
                .setStatus(PaymentStatus.PAID);

        ApiResp paymentApiResp = paymentService.create(createPaymentReq);

    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void query() {

        create();

        QueryPaymentOrderReq  queryPaymentOrderReq = new QueryPaymentOrderReq();
        Pagination<PaymentOrderInfo> paymentOrderInfoPagination = paymentOrderService.query(queryPaymentOrderReq);

        for( PaymentOrderInfo paymentOrderInfo : paymentOrderInfoPagination.getRecords() ){
            System.out.println("order记录："+paymentOrderInfo);

            QueryPaymentReq queryPaymentReq = new QueryPaymentReq();
            queryPaymentReq.setPayOrderSn(paymentOrderInfo.getSn());

            Pagination<PaymentInfo> paymentInfoPagination = paymentService.query(queryPaymentReq);
            for( PaymentInfo paymentInfo : paymentInfoPagination.getRecords() ){
                System.out.println("paymeny记录："+paymentInfo);
            }

        }


    }
}
