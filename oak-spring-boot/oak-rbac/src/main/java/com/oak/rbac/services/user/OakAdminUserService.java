package com.oak.rbac.services.user;

import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.CreateOakAdminUserReq;
import com.oak.rbac.services.user.req.DeleteOakAdminUserReq;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  管理员用户服务
 *  2020-1-16 18:28:37
 */
public interface OakAdminUserService {


    ApiResp<OakAdminUser> create(CreateOakAdminUserReq req);


    ApiResp<Void> edit(EditOakAdminUserReq req);


    ApiResp<Void> delete(DeleteOakAdminUserReq req);


    OakAdminUserInfo findById(Long id);


    Pagination<OakAdminUserInfo> query(QueryOakAdminUserReq req);

    /**
     * 通过token获取账号信息
     * @param token
     * @return
     */
    OakAdminUserInfo findByToken(String token);
}
