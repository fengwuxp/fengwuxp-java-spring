package com.oak.cms.services.adv;

import com.alibaba.druid.util.StringUtils;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.AdvPosition;
import com.oak.cms.services.adv.info.AdvPositionInfo;
import com.oak.cms.services.adv.req.CreateAdvPositionReq;
import com.oak.cms.services.adv.req.DeleteAdvPositionReq;
import com.oak.cms.services.adv.req.EditAdvPositionReq;
import com.oak.cms.services.adv.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 广告位信息服务
 * 2020-2-6 16:50:23
 *
 * @author chenPC
 */
@Service
@Slf4j
public class AdvPositionServiceImpl implements AdvPositionService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateAdvPositionReq req) {


        long codeC = jpaDao.selectFrom(AdvPosition.class)
                .eq("code", req.getCode())
                .count();
        if (codeC > 0) {
            return RestfulApiRespFactory.error("引用编码已被使用");
        }

        AdvPosition entity = new AdvPosition();
        BeanUtils.copyProperties(req, entity);

        entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditAdvPositionReq req) {


        if (!StringUtils.isEmpty(req.getCode())) {
            long codeC = jpaDao.selectFrom(AdvPosition.class)
                    .eq("code", req.getCode())
                    .appendWhere("id != ?", req.getId())
                    .count();
            if (codeC > 0) {
                return RestfulApiRespFactory.error("引用编码已被使用");
            }
        }

        AdvPosition entity = jpaDao.find(AdvPosition.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("广告位信息数据不存在");
        }

        UpdateDao<AdvPosition> updateDao = jpaDao.updateTo(AdvPosition.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新广告位信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteAdvPositionReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(AdvPosition.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(AdvPosition.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除广告位信息");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public AdvPositionInfo findById(Long id) {

        QueryAdvPositionReq queryReq = new QueryAdvPositionReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<AdvPositionInfo> query(QueryAdvPositionReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, AdvPosition.class, AdvPositionInfo.class, req);

    }
}
