package com.oak.admin.endpoint;

import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

/**
 * 前后端分离用于上报视图资源的端点
 */
@Slf4j
//@RestController
//@RequestMapping("/view/resource")
@RestControllerEndpoint(id = "/view/resource")
public class ViewUrlResourceIEndpoint {


    @Autowired
    private ResourceService resourceService;

    /**
     * 上报视图资源
     *
     * @return
     */
    @PostMapping("/report")
    public ApiResp<Void> reportViewResource(CreateResourceReq[] reqs) {

        Arrays.stream(reqs)
                .filter(createResourceReq -> {
                    QueryResourceReq req = new QueryResourceReq();
                    req.setQueryType(QueryType.QUERY_NUM);
                    req.setId(createResourceReq.getCode());
                    req.setType(ResourceType.VIEW_URL);
                    Pagination<ResourceInfo> permissionInfoPagination = resourceService.queryResource(req);
                    return permissionInfoPagination.getTotal() == 0;
                }).forEach(createResourceReq -> {
            resourceService.createResource(createResourceReq);
        });


        return RestfulApiRespFactory.ok();
    }
}
