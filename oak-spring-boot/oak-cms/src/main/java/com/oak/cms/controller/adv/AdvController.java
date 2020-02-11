package com.oak.cms.controller.adv;

import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.CreateAdvReq;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.adv.req.EditAdvReq;
import com.oak.cms.services.adv.req.QueryAdvReq;
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
@RequestMapping("/Adv")
@Tag(name = "广告信息", description = "广告信息管理")
@Slf4j
public class AdvController {


    @Autowired
    private AdvService advService;


    /**
     * 分页数据
     *
     * @param req QueryAdvReq
     * @return ApiResp<Pagination < AdvInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询Adv", description = "广告信息")
    public ApiResp<Pagination<AdvInfo>> query(QueryAdvReq req) {
        return RestfulApiRespFactory.ok(advService.query(req));
    }


    /**
     * 新增保存
     *
     * @param req CreateAdvEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建Adv", description = "广告信息")
    public ApiResp<Long> create(CreateAdvReq req) {
        return advService.create(req);
    }


    /**
     * 详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "详情Adv", description = "广告信息")
    public ApiResp<AdvInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(advService.findById(id));
    }


    /**
     * 修改保存
     */
    @PutMapping("/edit")
    @Operation(summary = "编辑Adv", description = "广告信息")
    public ApiResp<Void> edit(EditAdvReq req) {
        return advService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除Adv", description = "广告信息")
    public ApiResp<Void> delete(DeleteAdvReq req) {
        return advService.delete(req);
    }


}
