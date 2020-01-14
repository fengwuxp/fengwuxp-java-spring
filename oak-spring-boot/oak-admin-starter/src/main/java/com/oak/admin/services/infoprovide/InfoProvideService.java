package com.oak.admin.services.infoprovide;


import com.oak.admin.services.infoprovide.info.AreaInfo;
import com.oak.admin.services.infoprovide.req.FindAreaReq;
import com.oak.admin.services.infoprovide.req.QueryAreaReq;
import com.wuxp.api.model.Pagination;
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
    @Caching(cacheable = {
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'LIST_ID_'+#req.getId()",
                    condition = "#req.getFromCache() and #req.id != null",
                    unless = "#result.empty"),
            @Cacheable(value = AREA_CACHE_NAME,
                    key = "'LIST_T'+#req.thirdCode",
                    condition = "#req.getFromCache() and #req.thirdCode != null",
                    unless = "#result.empty")
    })
    Pagination<AreaInfo> queryArea(QueryAreaReq req);

    AreaInfo findAreaById(FindAreaReq req);

}
