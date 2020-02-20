package com.oak.member.services.token;

import com.oak.member.services.token.info.MemberTokenInfo;
import com.oak.member.services.token.req.CreateMemberTokenReq;
import com.oak.member.services.token.req.DeleteMemberTokenReq;
import com.oak.member.services.token.req.EditMemberTokenReq;
import com.oak.member.services.token.req.QueryMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  会员登录的token信息服务
 *  2020-2-18 16:22:54
 */
public interface MemberTokenService {


    ApiResp<Long> create(CreateMemberTokenReq req);


    ApiResp<Void> edit(EditMemberTokenReq req);


    ApiResp<Void> delete(DeleteMemberTokenReq req);


    MemberTokenInfo findById(Long id);


    Pagination<MemberTokenInfo> query(QueryMemberTokenReq req);

}
