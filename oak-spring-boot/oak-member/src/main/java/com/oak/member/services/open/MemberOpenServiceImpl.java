package com.oak.member.services.open;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberOpen;
import com.oak.member.services.open.info.MemberOpenInfo;
import com.oak.member.services.open.req.CreateMemberOpenReq;
import com.oak.member.services.open.req.DeleteMemberOpenReq;
import com.oak.member.services.open.req.EditMemberOpenReq;
import com.oak.member.services.open.req.QueryMemberOpenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  会员绑定信息服务
 *  2020-2-8 20:22:08
 */
@Service
@Slf4j
public class MemberOpenServiceImpl implements MemberOpenService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberOpenReq req) {


        MemberOpen entity = new MemberOpen();
        BeanUtils.copyProperties(req, entity);

            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberOpenReq req) {


        MemberOpen entity = jpaDao.find(MemberOpen.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("会员绑定信息数据不存在");
        }

        UpdateDao<MemberOpen> updateDao = jpaDao.updateTo(MemberOpen.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新会员绑定信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberOpenReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberOpen.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberOpen.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员绑定信息");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberOpenInfo findById(Long id) {

        QueryMemberOpenReq queryReq = new QueryMemberOpenReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberOpenInfo> query(QueryMemberOpenReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberOpen.class, MemberOpenInfo.class,req);

    }
}
