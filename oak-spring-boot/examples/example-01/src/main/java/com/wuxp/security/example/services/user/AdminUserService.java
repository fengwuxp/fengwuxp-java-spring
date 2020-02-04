package com.wuxp.security.example.services.user;


import com.wuxp.api.ApiResp;
import com.wuxp.security.example.services.user.info.LoginAdminUserInfo;
import com.wuxp.security.example.services.user.req.LoginAdminUserReq;


/**
* @author: zhuox
* @create: 2020-02-04
* @description: 管理员用户服务
**/
public interface AdminUserService {

    ApiResp<LoginAdminUserInfo> login(LoginAdminUserReq req);
}
