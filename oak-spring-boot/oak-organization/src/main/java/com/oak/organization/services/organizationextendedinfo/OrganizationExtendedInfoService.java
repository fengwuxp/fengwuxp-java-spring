package com.oak.organization.services.organizationextendedinfo;

import com.oak.organization.services.organizationextendedinfo.info.OrganizationExtendedInfoInfo;
import com.oak.organization.services.organizationextendedinfo.req.CreateOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.DeleteOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.EditOrganizationExtendedInfoReq;
import com.oak.organization.services.organizationextendedinfo.req.QueryOrganizationExtendedInfoReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  组织扩展信息服务
 *  2020-2-2 15:59:04
 */
public interface OrganizationExtendedInfoService {


    ApiResp<Long> create(CreateOrganizationExtendedInfoReq req);


    ApiResp<Void> edit(EditOrganizationExtendedInfoReq req);


    ApiResp<Void> delete(DeleteOrganizationExtendedInfoReq req);


    OrganizationExtendedInfoInfo findById(Long id);


    Pagination<OrganizationExtendedInfoInfo> query(QueryOrganizationExtendedInfoReq req);

}
