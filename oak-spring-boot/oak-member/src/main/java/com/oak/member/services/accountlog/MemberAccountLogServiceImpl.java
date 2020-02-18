package com.oak.member.services.accountlog;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberAccountLog;
import com.oak.member.services.accountlog.info.MemberAccountLogInfo;
import com.oak.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.oak.member.services.accountlog.req.DeleteMemberAccountLogReq;
import com.oak.member.services.accountlog.req.EditMemberAccountLogReq;
import com.oak.member.services.accountlog.req.QueryMemberAccountLogReq;
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
 *  会员账户信息日志服务
 *  2020-2-18 14:06:41
 */
@Service
@Slf4j
public class MemberAccountLogServiceImpl implements MemberAccountLogService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberAccountLogReq req) {


        MemberAccountLog entity = new MemberAccountLog();
        BeanUtils.copyProperties(req, entity);

            String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
            entity.setSn(sn);
            entity.setCreateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberAccountLogReq req) {


        MemberAccountLog entity = jpaDao.find(MemberAccountLog.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("会员账户信息日志数据不存在");
        }

        UpdateDao<MemberAccountLog> updateDao = jpaDao.updateTo(MemberAccountLog.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新会员账户信息日志失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberAccountLogReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberAccountLog.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberAccountLog.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员账户信息日志");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberAccountLogInfo findById(Long id) {

        QueryMemberAccountLogReq queryReq = new QueryMemberAccountLogReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberAccountLogInfo> query(QueryMemberAccountLogReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,MemberAccountLog.class,MemberAccountLogInfo.class,req);

    }
}
