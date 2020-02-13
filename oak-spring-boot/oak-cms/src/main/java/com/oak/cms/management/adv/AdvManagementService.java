package com.oak.cms.management.adv;

import com.oak.cms.management.adv.req.*;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * @author chenPC
 */
public interface AdvManagementService {

    /**
     * 分页查询广告
     *
     * @param req
     * @return
     */
    Pagination<AdvInfo> queryAdv(QueryAdvInfoReq req);

    /**
     * 添加广告
     *
     * @param req
     * @return
     */
    ApiResp<Long> createAdv(AddAdvInfoReq req);


    /**
     * 启用/禁用
     *
     * @param req
     * @return
     */
    ApiResp<Void> editEnabled(UpdateAdvReq req);


    /**
     * 删除广告
     *
     * @param req
     * @return
     */
    ApiResp<Void> deleteAdvById(DelAdvReq req);


    /**
     * 创建广告位
     *
     * @param req
     * @return
     */
    ApiResp<Long> createAdvPosition(AddAdvPositionReq req);


    /**
     * 删除广告位
     *
     * @param req
     * @return
     */
    ApiResp<Void> deleteAdvPosition(DelAdvPositionReq req);

    /**
     * 更新广告位
     *
     * @param req
     * @return
     */
    ApiResp<Void> editAdvPosition(UpdateAdvPositionReq req);

    /**
     * 查询广告位
     *
     * @param req
     * @return
     */
    Pagination<AdvPositionInfo> queryPositions(QueryAdvPositionInfoReq req);

}
