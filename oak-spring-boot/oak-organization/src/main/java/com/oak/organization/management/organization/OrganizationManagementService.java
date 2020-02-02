package com.oak.organization.management.organization;


import com.oak.organization.management.organization.req.AddOrganizationReq;
import com.oak.organization.management.organization.req.UpdateOrganizationExtendedInfoReq;
import com.oak.organization.management.organization.req.UpdateOrganizationReq;
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

    /**
     * 新增机构
     * @param req
     * @return
     */
    ApiResp<Long> addOrganization(AddOrganizationReq req);

    /**
     * 编辑机构
     * @param req
     * @return
     */
    ApiResp<Void> updateOrganization(UpdateOrganizationReq req);

    /**
     * 编辑机构拓展信息
     * @param req
     * @return
     */
    ApiResp<Void> updateOrganizationExtendedInfo(UpdateOrganizationExtendedInfoReq req);


}
