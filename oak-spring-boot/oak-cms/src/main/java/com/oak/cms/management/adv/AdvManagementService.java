package com.oak.cms.management.adv;

import com.oak.cms.management.adv.req.AddAdvInfoReq;
import com.oak.cms.management.adv.req.DelAdvReq;
import com.oak.cms.management.adv.req.QueryAdvInfoReq;
import com.oak.cms.management.adv.req.UpdateAdvReq;
import com.oak.cms.services.adv.info.AdvInfo;
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
    Pagination<AdvInfo> query(QueryAdvInfoReq req);

    /**
     * 添加广告
     *
     * @param req
     * @return
     */
    ApiResp<Long> create(AddAdvInfoReq req);


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
     * @param id
     * @return
     */
    ApiResp<Void> deleteAdvById(DelAdvReq req);
}
