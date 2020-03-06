package com.wuxp.miniprogram.controller;

import com.oak.organization.miniprogram.services.organizationminiprogramconfig.info.OrganizationMiniProgramConfigInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.log.ApiLog;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.OrganizationMiniProgramConfigService;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.CreateOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.DeleteOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.EditOrganizationMiniProgramConfigReq;
import com.wuxp.miniprogram.services.organizationminiprogramconfig.req.QueryOrganizationMiniProgramConfigReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("OrganizationMiniProgramConfigController")
@RequestMapping("/v1/organizationminiprogramconfig")
@Tag(name = "组织第三方小程序发布配置", description = "组织（服务商）第三方小程序发布配置")
@Slf4j
public class OrganizationMiniProgramConfigController {


    @Autowired
    private OrganizationMiniProgramConfigService organizationMiniProgramConfigService;

    /**
     * 分页数据
     *
     * @param req  QueryOrganizationMiniProgramConfigReq
     * @return  ApiResp<Pagination<OrganizationMiniProgramConfigInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询OrganizationMiniProgramConfig", description = "查询发布配置")
    public ApiResp<Pagination<OrganizationMiniProgramConfigInfo>> query(QueryOrganizationMiniProgramConfigReq req) {
        return RestfulApiRespFactory.ok(organizationMiniProgramConfigService.query(req));
    }




    /**
     * 新增保存
     *
     * @param req   CreateOrganizationMiniProgramConfigEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建OrganizationMiniProgramConfig", description = "新添加小程序发布配置")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateOrganizationMiniProgramConfigReq req) {
        return organizationMiniProgramConfigService.create(req);
    }



    /**
    * 详情
    *
    * @param id Long
    */
    @GetMapping("/{id}")
    @Operation(summary = "详情OrganizationMiniProgramConfig", description = "查看小程序发布配置细节")
    public ApiResp<OrganizationMiniProgramConfigInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(organizationMiniProgramConfigService.findById(id));
     }


    /**
     * 修改保存
     */
     @PutMapping("/edit")
     @Operation(summary = "编辑OrganizationMiniProgramConfig", description = "编辑小程序发布配置")
     public ApiResp<Void> edit(EditOrganizationMiniProgramConfigReq req) {
         return organizationMiniProgramConfigService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除OrganizationMiniProgramConfig", description = "修改小程序发布配置")
    public ApiResp<Void> delete(DeleteOrganizationMiniProgramConfigReq req) {
        return organizationMiniProgramConfigService.delete(req);
    }


}
