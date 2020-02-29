package com.oak.cms.services.advposition;

import com.oak.cms.services.advposition.info.AdvPositionInfo;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.oak.cms.services.advposition.req.DeleteAdvPositionReq;
import com.oak.cms.services.advposition.req.EditAdvPositionReq;
import com.oak.cms.services.advposition.req.QueryAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  广告位信息服务
 *  2020-2-12 18:54:50
 */
public interface AdvPositionService {


    ApiResp<Long> create(CreateAdvPositionReq req);


    ApiResp<Void> edit(EditAdvPositionReq req);


    ApiResp<Void> delete(DeleteAdvPositionReq req);


    AdvPositionInfo findById(Long id);


    Pagination<AdvPositionInfo> query(QueryAdvPositionReq req);

}
