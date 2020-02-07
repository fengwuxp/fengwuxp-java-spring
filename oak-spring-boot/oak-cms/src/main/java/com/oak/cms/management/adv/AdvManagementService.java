package com.oak.cms.management.adv;

import com.oak.cms.management.adv.req.AddAdvPositionReq;
import com.oak.cms.management.adv.req.DelAdvPositionReq;
import com.oak.cms.management.adv.req.QueryAdvPositionsReq;
import com.oak.cms.management.adv.req.UpdateAdvPositionReq;
import com.oak.cms.services.adv.info.AdvPositionInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * 广告位管理服务
 *
 * @author chenPC
 */
public interface AdvManagementService {


    /**
     * 添加广告位
     *
     * @param req
     * @return
     */
    ApiResp<Long> addAdvPosition(AddAdvPositionReq req);


    /**
     * 编辑广告位
     *
     * @param req
     * @return
     */
    ApiResp<Void> editAdvPosition(UpdateAdvPositionReq req);


    /**
     * 根据id删除广告位
     *
     * @param req
     * @return
     */
    ApiResp<Void> deleteAdvPosition(DelAdvPositionReq req);


    /**
     * 根据ID查找
     *
     * @param id
     * @return
     */
    AdvPositionInfo findById(Long id);

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    Pagination<AdvPositionInfo> query(QueryAdvPositionsReq req);

}
