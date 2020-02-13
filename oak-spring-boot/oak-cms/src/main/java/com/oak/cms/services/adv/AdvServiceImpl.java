package com.oak.cms.services.adv;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.Adv;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 广告信息服务
 * 2020-2-10 18:55:01
 *
 * @author chenPC
 */
@Service
@Slf4j
public class AdvServiceImpl implements AdvService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateAdvReq req) {


        Adv entity = new Adv();
        BeanUtils.copyProperties(req, entity);

        entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditAdvReq req) {


        Adv entity = jpaDao.find(Adv.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("广告信息数据不存在");
        }

        UpdateDao<Adv> updateDao = jpaDao.updateTo(Adv.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新广告信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteAdvReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Adv.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Adv.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public AdvInfo findById(Long id) {

        QueryAdvReq queryReq = new QueryAdvReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<AdvInfo> query(QueryAdvReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Adv.class, AdvInfo.class, req);

    }
}
