package com.oak.member.services.member;

import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.CreateMemberReq;
import com.oak.member.services.member.req.DeleteMemberReq;
import com.oak.member.services.member.req.EditMemberReq;
import com.oak.member.services.member.req.QueryMemberReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  会员信息服务
 *  2020-2-6 15:32:43
 */
public interface MemberService {


    ApiResp<Long> create(CreateMemberReq req);


    ApiResp<Void> edit(EditMemberReq req);


    ApiResp<Void> delete(DeleteMemberReq req);


    MemberInfo findById(Long id);


    Pagination<MemberInfo> query(QueryMemberReq req);

}
