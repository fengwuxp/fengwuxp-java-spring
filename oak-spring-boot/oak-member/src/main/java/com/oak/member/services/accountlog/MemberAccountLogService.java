package com.oak.member.services.accountlog;

import com.oak.member.services.accountlog.info.MemberAccountLogInfo;
import com.oak.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.oak.member.services.accountlog.req.DeleteMemberAccountLogReq;
import com.oak.member.services.accountlog.req.EditMemberAccountLogReq;
import com.oak.member.services.accountlog.req.QueryMemberAccountLogReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  会员账户信息日志服务
 *  2020-2-18 14:06:41
 */
public interface MemberAccountLogService {


    ApiResp<Long> create(CreateMemberAccountLogReq req);


    ApiResp<Void> edit(EditMemberAccountLogReq req);


    ApiResp<Void> delete(DeleteMemberAccountLogReq req);


    MemberAccountLogInfo findById(Long id);


    Pagination<MemberAccountLogInfo> query(QueryMemberAccountLogReq req);

}
