package com.oak.rbac.services.user;

import com.oak.rbac.entities.OakAdminUser;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.rbac.services.user.req.*;
import com.oak.rbac.services.user.info.OakAdminUserInfo;



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

}
