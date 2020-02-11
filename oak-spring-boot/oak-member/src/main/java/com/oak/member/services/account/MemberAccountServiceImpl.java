package com.oak.member.services.account;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberAccount;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.account.req.DeleteMemberAccountReq;
import com.oak.member.services.account.req.EditMemberAccountReq;
import com.oak.member.services.account.req.QueryMemberAccountReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 会员账户信息服务
 * 2020-2-6 15:42:46
 */
@Service
@Slf4j
public class MemberAccountServiceImpl implements MemberAccountService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberAccountReq req) {


        MemberAccount entity = new MemberAccount();
        BeanUtils.copyProperties(req, entity);

        entity.setCreateTime(new Date());

        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberAccountReq req) {


        MemberAccount entity = jpaDao.find(MemberAccount.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员账户信息数据不存在");
        }

        UpdateDao<MemberAccount> updateDao = jpaDao.updateTo(MemberAccount.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新会员账户信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberAccountReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberAccount.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberAccount.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员账户信息");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberAccountInfo findById(Long id) {

        QueryMemberAccountReq queryReq = new QueryMemberAccountReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberAccountInfo> query(QueryMemberAccountReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberAccount.class, MemberAccountInfo.class, req);

    }
}
