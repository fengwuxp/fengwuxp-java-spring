package com.oak.cms.controller.adv;

import com.oak.cms.management.adv.AdvManagementService;
import com.oak.cms.management.adv.req.AddAdvInfoReq;
import com.oak.cms.management.adv.req.DelAdvReq;
import com.oak.cms.management.adv.req.QueryAdvInfoReq;
import com.oak.cms.management.adv.req.UpdateAdvReq;
import com.oak.cms.services.adv.AdvService;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
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
    @Autowired
    private AdvManagementService advManagementService;

    @Autowired
    private AdvPositionService advPositionService;


    /**
     * 广告分页数据
     *
     * @param req QueryAdvReq
     * @return ApiResp<Pagination < AdvInfo>>
     */
    @GetMapping("/query_adv")
    @Operation(summary = "查询广告", description = "查询广告")
    public ApiResp<Pagination<AdvInfo>> queryAdvInfo(QueryAdvInfoReq req) {
        return RestfulApiRespFactory.ok(advManagementService.queryAdv(req));
    }


    /**
     * 新增保存
     *
     * @param req CreateAdvEvt
     * @return ApiResp
     */
    @PostMapping("/add_adv")
    @Operation(summary = "添加广告", description = "添加广告")
    public ApiResp<Long> AddAdv(AddAdvInfoReq req) {
        return advManagementService.createAdv(req);
    }


    /**
     * 广告详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "广告详情", description = "广告详情")
    public ApiResp<AdvInfo> detailAdv(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(advService.findById(id));
    }


    /**
     * 修改广告
     */
    @PutMapping("/edit_adv")
    @Operation(summary = "修改广告", description = "修改广告")
    public ApiResp<Void> editAdv(UpdateAdvReq req) {
        return advManagementService.editAdv(req);
    }


    /**
     * 删除广告
     */
    @GetMapping("/del_adv")
    @Operation(summary = "删除广告", description = "删除广告")
    public ApiResp<Void> deleteAdv(DelAdvReq req) {
        return advManagementService.deleteAdvById(req);
    }


    /**
     * 广告位分页数据
     *
     * @param req QueryAdvPositionReq
     * @return ApiResp<Pagination < AdvPositionInfo>>
     */
    @GetMapping("/query_adv_position")
    @Operation(summary = "查询广告位", description = "查询广告位")
    public ApiResp<Pagination<AdvPositionInfo>> queryAdvPosition(QueryAdvPositionReq req) {
        return RestfulApiRespFactory.ok(advPositionService.query(req));
    }


    /**
     * 创建广告位
     *
     * @param req CreateAdvPositionEvt
     * @return ApiResp
     */
    @PostMapping("/add_adv_position")
    @Operation(summary = "创建广告位", description = "创建广告位")
    public ApiResp<Long> addAdvPosition(CreateAdvPositionReq req) {
        return advPositionService.create(req);
    }


    /**
     * 详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "广告位详情", description = "广告位详情")
    public ApiResp<AdvPositionInfo> AdvPositionDetailById(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(advPositionService.findById(id));
    }


    /**
     * 修改保存
     */
    @PutMapping("/edit_adv_position")
    @Operation(summary = "广告位更新", description = "广告位更新")
    public ApiResp<Void> editAdvPosition(EditAdvPositionReq req) {
        return advPositionService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/del_adv_position")
    @Operation(summary = "删除广告位", description = "删除广告位")
    public ApiResp<Void> delAdvPosition(DeleteAdvPositionReq req) {
        return advPositionService.delete(req);
    }
}
