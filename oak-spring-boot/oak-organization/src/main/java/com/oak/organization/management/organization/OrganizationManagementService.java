package com.oak.organization.management.organization;


import com.oak.organization.management.organization.req.RegisterOrganizationReq;
import com.wuxp.api.ApiResp;

/**
 * 组织管理服务
 */
public interface OrganizationManagementService {


    /**
     * 注册账号
     *
     * @param req
     * @return
     */
    ApiResp<Long> registerOrganization(RegisterOrganizationReq req);







}
