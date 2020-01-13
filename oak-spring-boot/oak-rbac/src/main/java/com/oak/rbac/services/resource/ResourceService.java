package com.oak.rbac.services.resource;


import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.DeleteResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.model.Pagination;

/**
 * 资源服务
 */
public interface ResourceService {


    String RESOURCE_CACHE_NAME="SYSTEM_RESOURCE_CACHE";

    /**
     * 创建资源
     *
     * @param req
     * @return
     */
    void createResource(CreateResourceReq req);


    /**
     * 删除 resource
     *
     * @param req
     * @return
     */
    int deleteResource(DeleteResourceReq req);



    /**
     * 查询资源
     *
     * @param req
     * @return
     */
    Pagination<ResourceInfo> queryResource(QueryResourceReq req);

}
