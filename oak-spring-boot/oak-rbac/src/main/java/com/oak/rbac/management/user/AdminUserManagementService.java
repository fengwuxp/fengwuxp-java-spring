package com.oak.rbac.management.user;


import com.oak.rbac.management.user.info.LoginAdminUserInfo;
import com.oak.rbac.management.user.req.LoginAdminUserReq;
import com.wuxp.api.ApiResp;


/**
* @author: zhuox
* @create: 2020-02-04
* @description: 管理员用户服务
**/
public interface AdminUserManagementService {

    ApiResp<LoginAdminUserInfo> login(LoginAdminUserReq req);
}
