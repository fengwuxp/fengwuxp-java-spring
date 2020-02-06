package com.oak.cms.controller.adv;

import com.oak.cms.services.adv.AdvPositionService;
import com.oak.cms.services.adv.info.AdvPositionInfo;
import com.oak.cms.services.adv.req.CreateAdvPositionReq;
import com.oak.cms.services.adv.req.DeleteAdvPositionReq;
import com.oak.cms.services.adv.req.EditAdvPositionReq;
import com.oak.cms.services.adv.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author chenPC
 */
@RestController
@RequestMapping("/AdvPosition")
@Tag(name = "广告位信息", description = "广告位信息管理")
@Slf4j
public class AdvPositionController {


    @Autowired
    private AdvPositionService advPositionService;


    /**
     * 分页数据
     *
     * @param req QueryAdvPositionReq
     * @return ApiResp<Pagination < AdvPositionInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询AdvPosition", description = "广告位信息")
    public ApiResp<Pagination<AdvPositionInfo>> query(QueryAdvPositionReq req) {
        return RestfulApiRespFactory.ok(advPositionService.query(req));
    }


    /**
     * 新增保存
     *
     * @param req CreateAdvPositionEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建AdvPosition", description = "广告位信息")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateAdvPositionReq req) {
        return advPositionService.create(req);
    }


    /**
     * 详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "详情AdvPosition", description = "广告位信息")
    public ApiResp<AdvPositionInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(advPositionService.findById(id));
    }


    /**
     * 修改保存
     */
    @PutMapping("/edit")
    @Operation(summary = "编辑AdvPosition", description = "广告位信息")
    public ApiResp<Void> edit(EditAdvPositionReq req) {
        return advPositionService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除AdvPosition", description = "广告位信息")
    public ApiResp<Void> delete(DeleteAdvPositionReq req) {
        return advPositionService.delete(req);
    }


}
