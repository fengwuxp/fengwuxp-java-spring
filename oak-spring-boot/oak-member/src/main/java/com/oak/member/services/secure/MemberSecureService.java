package com.oak.member.services.secure;

import com.oak.member.services.secure.info.LoginFail;
import com.oak.member.services.secure.info.MemberSecureInfo;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.oak.member.services.secure.req.DeleteMemberSecureReq;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.oak.member.services.secure.req.QueryMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  MemberSecure服务
 *  2020-2-6 16:00:48
 */
public interface MemberSecureService {


    ApiResp<Long> create(CreateMemberSecureReq req);


    ApiResp<Void> edit(EditMemberSecureReq req);


    ApiResp<Void> delete(DeleteMemberSecureReq req);


    MemberSecureInfo findById(Long id);


    Pagination<MemberSecureInfo> query(QueryMemberSecureReq req);

    LoginFail findLoginFail(Long uid);

    LoginFail saveLoginFail(Long uid, LoginFail loginFail);

    Integer addLoginFail(Long id);

    void resetLoginFail(Long uid);
}
