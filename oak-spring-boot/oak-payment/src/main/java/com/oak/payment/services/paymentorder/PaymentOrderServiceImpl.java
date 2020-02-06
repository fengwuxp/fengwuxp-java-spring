package com.oak.payment.services.paymentorder;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.payment.entities.PaymentOrder;
import com.oak.payment.services.paymentorder.info.PaymentOrderInfo;
import com.oak.payment.services.paymentorder.req.CreatePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.DeletePaymentOrderReq;
import com.oak.payment.services.paymentorder.req.EditPaymentOrderReq;
import com.oak.payment.services.paymentorder.req.QueryPaymentOrderReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


/**
 *  支付订单对象服务
 *  2020-2-6 11:31:05
 */
@Service
@Slf4j
public class PaymentOrderServiceImpl implements PaymentOrderService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<String> create(CreatePaymentOrderReq req) {


        PaymentOrder entity = new PaymentOrder();
        BeanUtils.copyProperties(req, entity);

            String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
            entity.setSn(sn);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getSn());
    }

    @Override
    public ApiResp<Void> edit(EditPaymentOrderReq req) {


        PaymentOrder entity = jpaDao.find(PaymentOrder.class, req.getSn());
        if (entity == null) {
            return  RestfulApiRespFactory.error("支付订单对象数据不存在");
        }

        UpdateDao<PaymentOrder> updateDao = jpaDao.updateTo(PaymentOrder.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新支付订单对象失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeletePaymentOrderReq req) {


        if (req.getSn() == null
                && (req.getSns() == null || req.getSns().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(PaymentOrder.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(PaymentOrder.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除支付订单对象");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public PaymentOrderInfo findById(String sn) {

        QueryPaymentOrderReq queryReq = new QueryPaymentOrderReq();
        queryReq.setSn(sn);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<PaymentOrderInfo> query(QueryPaymentOrderReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,PaymentOrder.class,PaymentOrderInfo.class,req);

    }
}
