package com.oak.organization.services.organization;

import com.oak.organization.entities.Organization;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.oak.organization.services.organization.req.*;
import com.oak.organization.services.organization.info.OrganizationInfo;


/**
 * 组织服务
 * 2020-1-19 13:18:21
 */
public interface OrganizationService {

    String ORGANIZATION_CACHE_NAME = " ORGANIZATION_CACHE";

    String ORGANIZATION_CACHE_SINGLE_CACHE = "ORGANIZATION_SINGLE_CACHE";


    ApiResp<Organization> create(CreateOrganizationReq req);


    ApiResp<Void> edit(EditOrganizationReq req);


    ApiResp<Void> delete(DeleteOrganizationReq req);


    OrganizationInfo findById(Long id);


    Pagination<OrganizationInfo> query(QueryOrganizationReq req);

}
