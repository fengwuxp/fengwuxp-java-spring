package com.oak.member.services.secure;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberSecure;
import com.oak.member.services.secure.info.MemberSecureInfo;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.oak.member.services.secure.req.DeleteMemberSecureReq;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.oak.member.services.secure.req.QueryMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  MemberSecure服务
 *  2020-2-6 16:00:48
 */
@Service
@Slf4j
public class MemberSecureServiceImpl implements MemberSecureService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberSecureReq req) {


        MemberSecure entity = new MemberSecure();
        BeanUtils.copyProperties(req, entity);

            entity.setUpdateTime(new Date());

        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberSecureReq req) {


        MemberSecure entity = jpaDao.find(MemberSecure.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("MemberSecure数据不存在");
        }

        UpdateDao<MemberSecure> updateDao = jpaDao.updateTo(MemberSecure.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新MemberSecure失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberSecureReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberSecure.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberSecure.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除MemberSecure");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberSecureInfo findById(Long id) {

        QueryMemberSecureReq queryReq = new QueryMemberSecureReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberSecureInfo> query(QueryMemberSecureReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberSecure.class,MemberSecureInfo.class,req);

    }
}
