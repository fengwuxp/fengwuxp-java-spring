package com.oak.organization.controller;

import com.wuxp.api.ApiResp;
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


    @RequestMapping("/create")
    public ApiResp<Long> create() {


        return RestfulApiRespFactory.ok();
    }

}
