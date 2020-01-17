package com.oak.admin.services.infoprovide;


import com.oak.admin.services.infoprovide.info.AreaInfo;
import com.oak.admin.services.infoprovide.req.EditAreaReq;
import com.oak.admin.services.infoprovide.req.FindAreaReq;
import com.oak.admin.services.infoprovide.req.QueryAreaReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

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
