package com.oak.member.services.token;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberToken;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.oak.member.services.token.req.CreateMemberTokenReq;
import com.oak.member.services.token.req.DeleteMemberTokenReq;
import com.oak.member.services.token.req.EditMemberTokenReq;
import com.oak.member.services.token.req.QueryMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  会员登录的token信息服务
 *  2020-2-18 16:22:54
 */
@Service
@Slf4j
public class MemberTokenServiceImpl implements MemberTokenService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberTokenReq req) {


        MemberToken entity = new MemberToken();
        BeanUtils.copyProperties(req, entity);


        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberTokenReq req) {


        MemberToken entity = jpaDao.find(MemberToken.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("会员登录的token信息数据不存在");
        }

        UpdateDao<MemberToken> updateDao = jpaDao.updateTo(MemberToken.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新会员登录的token信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberTokenReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberToken.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberToken.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员登录的token信息");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberTokenInfo findById(Long id) {

        QueryMemberTokenReq queryReq = new QueryMemberTokenReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberTokenInfo> query(QueryMemberTokenReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,MemberToken.class,MemberTokenInfo.class,req);

    }

}
