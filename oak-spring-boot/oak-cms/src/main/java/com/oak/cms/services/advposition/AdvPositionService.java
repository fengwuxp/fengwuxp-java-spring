package com.oak.cms.services.advposition;

import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 广告位信息服务
 * 2020-2-6 16:50:23
 *
 * @author chenPC
 */
public interface AdvPositionService {


    /**
     * 添加
     *
     * @param req
     * @return
     */
    ApiResp<Long> create(CreateAdvPositionReq req);


    /**
     * 编辑
     *
     * @param req
     * @return
     */
    ApiResp<Void> edit(EditAdvPositionReq req);


    /**
     * 删除
     *
     * @param req
     * @return
     */
    ApiResp<Void> delete(DeleteAdvPositionReq req);


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
    Pagination<AdvPositionInfo> query(QueryAdvPositionReq req);

}
