package com.oak.api.controller;

import com.oak.api.services.infoprovide.InfoProvideService;
import com.oak.api.services.infoprovide.info.AreaInfo;
import com.oak.api.services.infoprovide.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 区域
 */
@RestController(value = "AreaController")
@RequestMapping("/area")
@Tag(name = "区域", description = "区域相关")
@Slf4j
public class AreaController {

    @Autowired
    private InfoProvideService infoProvideService;

    /**
     * 查询地区列表
     * @param req
     * @return
     */
    @Operation(summary = "查询地区列表", description = "查询地区列表")
    @GetMapping("")
    public ApiResp<Pagination<AreaInfo>> queryArea(QueryAreaReq req) {
        return RestfulApiRespFactory.queryOk(infoProvideService.queryArea(req));
    }
}
