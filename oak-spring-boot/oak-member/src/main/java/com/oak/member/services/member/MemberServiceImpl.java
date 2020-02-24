package com.oak.member.services.member;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_Member;
import com.oak.member.entities.Member;
import com.oak.member.helper.SnHelper;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * 会员信息服务
 * 2020-2-6 15:32:43
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberReq req) {
        //生成member no
        String no = SnHelper.generateShortUuid();
        req.setNo(no);

        long noC = jpaDao.selectFrom(Member.class)
                .eq("no", req.getNo())
                .count();
        if (noC > 0) {
            return RestfulApiRespFactory.error("系统忙，请重试");
        }

        long userNameC = jpaDao.selectFrom(Member.class)
                .eq("userName", req.getUserName())
                .count();
        if (userNameC > 0) {
            return RestfulApiRespFactory.error("用户名已被使用");
        }

        Member entity = new Member();
        BeanUtils.copyProperties(req, entity);

        entity.setCreateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberReq req) {

        if (!StringUtils.isEmpty(req.getUserName())) {
            long userNameC = jpaDao.selectFrom(Member.class)
                    .eq("userName", req.getUserName())
                    .appendWhere("id != ?", req.getId())
                    .count();
            if (userNameC > 0) {
                return RestfulApiRespFactory.error("用户名已被使用");
            }
        }

        Member entity = jpaDao.find(Member.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员信息数据不存在");
        }

        UpdateDao<Member> updateDao = jpaDao.updateTo(Member.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新会员信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Member.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Member.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员信息");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberInfo findById(Long id) {

        QueryMemberReq queryReq = new QueryMemberReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberInfo> query(QueryMemberReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Member.class, MemberInfo.class, req);

    }

    @Override
    public ApiResp<Long> checkMember(CheckMemberReq req) {
        Long memberId = jpaDao.selectFrom(Member.class)
                .select(E_Member.id)
                .appendWhere(StringUtils.hasText(req.getUserName()), E_Member.userName + " = ?", req.getUserName())
                .appendWhere(StringUtils.hasText(req.getMobilePhone()), E_Member.mobilePhone + " = ?",req.getMobilePhone())
                .appendWhere(StringUtils.hasText(req.getEmail()), E_Member.email + " = ?", req.getEmail())
                .findOne();
        return RestfulApiRespFactory.ok(memberId);
    }
}
