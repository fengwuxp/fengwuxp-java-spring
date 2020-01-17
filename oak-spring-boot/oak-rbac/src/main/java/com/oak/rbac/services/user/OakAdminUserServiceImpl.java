package com.oak.rbac.services.user;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import org.springframework.beans.BeanUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.req.*;
import com.oak.rbac.services.user.info.OakAdminUserInfo;

import java.util.Date;


/**
 * 管理员用户服务
 * 2020-1-16 18:28:37
 */
@Service
@Slf4j
public class OakAdminUserServiceImpl implements OakAdminUserService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateOakAdminUserReq req) {


        OakAdminUser entity = new OakAdminUser();
        BeanUtils.copyProperties(req, entity);

        Date time = new Date();
        entity.setCreateTime(time);
        entity.setLastUpdateTime(time);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditOakAdminUserReq req) {


        OakAdminUser entity = jpaDao.find(OakAdminUser.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("管理员用户数据不存在");
        }

        UpdateDao<OakAdminUser> updateDao = jpaDao.updateTo(OakAdminUser.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新管理员用户失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteOakAdminUserReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(OakAdminUser.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(OakAdminUser.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除管理员用户");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OakAdminUserInfo findById(Long id) {

        QueryOakAdminUserReq queryReq = new QueryOakAdminUserReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<OakAdminUserInfo> query(QueryOakAdminUserReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, OakAdminUser.class, OakAdminUserInfo.class, req);

    }
}
