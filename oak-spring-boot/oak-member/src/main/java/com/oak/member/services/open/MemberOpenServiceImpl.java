package com.oak.member.services.open;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_MemberOpen;
import com.oak.member.entities.MemberOpen;
import com.oak.member.enums.OpenType;
import com.oak.member.services.open.info.MemberOpenInfo;
import com.oak.member.services.open.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

    @Override
    public ApiResp<Boolean> checkBindOpen(CheckBindOpenReq req) {
        List<OpenType> wxOpenTypes = new ArrayList<>();
        wxOpenTypes.add(OpenType.WEIXIN);
        wxOpenTypes.add(OpenType.WEIXIN_OPEN);
        wxOpenTypes.add(OpenType.WEIXIN_MA);
        SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                .eq(E_MemberOpen.memberId, req.getMemberId())
                .appendWhere(!StringUtils.isEmpty(req.getOpenId()) && !StringUtils.isEmpty(req.getUnionId()),
                        "( openId = ? or unionId = ?)", req.getOpenId(), req.getUnionId())
                .eq(E_MemberOpen.openId, req.getOpenId())
                .eq(E_MemberOpen.unionId, req.getUnionId());

        if (wxOpenTypes.contains(req.getOpenType())) {
            selectDao.in(E_MemberOpen.openType, wxOpenTypes.toArray());
        } else {
            selectDao.eq(E_MemberOpen.openType, req.getOpenType());
        }

        return RestfulApiRespFactory.ok(selectDao.count() > 0);
    }
}
