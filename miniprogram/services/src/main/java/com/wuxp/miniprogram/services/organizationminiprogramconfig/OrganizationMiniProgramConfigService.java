package com.wuxp.miniprogram.services.organizationminiprogramconfig;

import com.oak.organization.miniprogram.services.organizationminiprogramconfig.info.OrganizationMiniProgramConfigInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.CreateOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.DeleteOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.EditOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.QueryOrganizationMiniProgramConfigReq;
import io.swagger.v3.oas.annotations.media.Schema;



/**
 *  组织小程序发布配置服务
 *  2020-3-2 17:28:21
 */
public interface OrganizationMiniProgramConfigService {


    ApiResp<Long> create(CreateOrganizationMiniProgramConfigReq req);


    ApiResp<Void> edit(EditOrganizationMiniProgramConfigReq req);


    ApiResp<Void> delete(DeleteOrganizationMiniProgramConfigReq req);


    OrganizationMiniProgramConfigInfo findById(Long id);


    Pagination<OrganizationMiniProgramConfigInfo> query(QueryOrganizationMiniProgramConfigReq req);

}
