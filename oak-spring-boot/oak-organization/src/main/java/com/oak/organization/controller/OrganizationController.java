package com.oak.organization.controller;

import com.oak.organization.constant.OrganizationApiLogTypeConstant;
import com.oak.organization.management.organization.OrganizationManagementService;
import com.oak.organization.management.organization.req.RegisterOrganizationReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/organization")
@Slf4j
@Tag(name = "机构相关", description = "组织相关服务")
public class OrganizationController {


    private OrganizationManagementService organizationManagementService;


    @RequestMapping("/create")
    @ApiLog(value = "'创建机构，名称['+#req.name+']'", type = OrganizationApiLogTypeConstant.机构)
    public ApiResp<Long> create(RegisterOrganizationReq req) {


        return organizationManagementService.registerOrganization(req);
    }

}
