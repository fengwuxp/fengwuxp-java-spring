package com.oak.api.services.infoprovide;


import com.oak.api.services.infoprovide.info.AreaInfo;
import com.oak.api.services.infoprovide.req.EditAreaReq;
import com.oak.api.services.infoprovide.req.FindAreaReq;
import com.oak.api.services.infoprovide.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * 数据维护服务
 */
public interface InfoProvideService {

    String AREA_CACHE_NAME = "AREA_CACHE";

    /**
     * 地区查询
     *
     * @param req
     * @return
     */
    Pagination<AreaInfo> queryArea(QueryAreaReq req);


    AreaInfo findAreaById(FindAreaReq req);


    ApiResp<Void> editArea(EditAreaReq req);
}
