package com.oak.payment.services.payment;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.payment.entities.Payment;
import com.oak.payment.services.payment.info.PaymentInfo;
import com.oak.payment.services.payment.req.CreatePaymentReq;
import com.oak.payment.services.payment.req.DeletePaymentReq;
import com.oak.payment.services.payment.req.EditPaymentReq;
import com.oak.payment.services.payment.req.QueryPaymentReq;
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
 *  支付单对象服务
 *  2020-2-6 11:21:50
 */
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<String> create(CreatePaymentReq req) {


        Payment entity = new Payment();
        BeanUtils.copyProperties(req, entity);

            String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
            entity.setSn(sn);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getSn());
    }

    @Override
    public ApiResp<Void> edit(EditPaymentReq req) {


        Payment entity = jpaDao.find(Payment.class, req.getSn());
        if (entity == null) {
            return  RestfulApiRespFactory.error("支付单对象数据不存在");
        }

        UpdateDao<Payment> updateDao = jpaDao.updateTo(Payment.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新支付单对象失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeletePaymentReq req) {


        if (req.getSn() == null
                && (req.getSns() == null || req.getSns().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Payment.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Payment.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除支付单对象");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public PaymentInfo findById(String sn) {

        QueryPaymentReq queryReq = new QueryPaymentReq();
        queryReq.setSn(sn);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<PaymentInfo> query(QueryPaymentReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,Payment.class,PaymentInfo.class,req);

    }
}
