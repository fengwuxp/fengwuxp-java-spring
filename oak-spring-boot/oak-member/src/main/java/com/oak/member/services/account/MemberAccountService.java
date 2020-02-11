package com.oak.member.services.account;

import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.account.req.DeleteMemberAccountReq;
import com.oak.member.services.account.req.EditMemberAccountReq;
import com.oak.member.services.account.req.QueryMemberAccountReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  会员账户信息服务
 *  2020-2-6 15:42:46
 */
public interface MemberAccountService {


    ApiResp<Long> create(CreateMemberAccountReq req);


    ApiResp<Void> edit(EditMemberAccountReq req);


    ApiResp<Void> delete(DeleteMemberAccountReq req);


    MemberAccountInfo findById(Long id);


    Pagination<MemberAccountInfo> query(QueryMemberAccountReq req);

}
