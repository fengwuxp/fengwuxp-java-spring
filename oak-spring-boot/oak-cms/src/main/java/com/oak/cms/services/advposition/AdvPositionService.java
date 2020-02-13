package com.oak.cms.services.advposition;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.cms.services.advposition.req.*;
import com.oak.cms.services.advposition.info.AdvPositionInfo;



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
