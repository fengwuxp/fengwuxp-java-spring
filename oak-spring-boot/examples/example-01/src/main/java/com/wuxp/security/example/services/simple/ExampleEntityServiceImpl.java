package com.wuxp.security.example.services.simple;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.example.entities.ExampleEntity;
import com.wuxp.security.example.services.simple.info.ExampleEntityInfo;
import com.wuxp.security.example.services.simple.req.CreateExampleEntityReq;
import com.wuxp.security.example.services.simple.req.DeleteExampleEntityReq;
import com.wuxp.security.example.services.simple.req.EditExampleEntityReq;
import com.wuxp.security.example.services.simple.req.QueryExampleEntityReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * example例子服务
 * 2020-2-16 10:20:18
 */
@Service
@Slf4j
public class ExampleEntityServiceImpl implements ExampleEntityService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateExampleEntityReq req) {


        ExampleEntity entity = new ExampleEntity();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setDeleted(false);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditExampleEntityReq req) {


        ExampleEntity entity = jpaDao.find(ExampleEntity.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("example例子数据不存在");
        }

        UpdateDao<ExampleEntity> updateDao = jpaDao.updateTo(ExampleEntity.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新example例子失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteExampleEntityReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(ExampleEntity.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(ExampleEntity.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除example例子");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ExampleEntityInfo findById(Long id) {

        QueryExampleEntityReq queryReq = new QueryExampleEntityReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ExampleEntityInfo> query(QueryExampleEntityReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, ExampleEntity.class, ExampleEntityInfo.class, req);

    }
}
