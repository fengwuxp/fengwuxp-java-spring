package com.oak.admin.endpoint;

import com.oak.admin.services.menu.MenuService;
import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 前后端分离用于上报视图资源的端点
 */
@Slf4j
@RestController
@RequestMapping("/view/resource")
//@RestControllerEndpoint(id = "/view/resource")
public class ViewUrlResourceEndpoint {


    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MenuService menuService;


    /**
     * 上报客户端视图路由
     * 通过客户端路由信息生成默认的菜单列表和资源列表
     *
     * @return
     */
    @PostMapping("/report/view_routes")
    public ApiResp<Void> reportViewRoutes() {


        return RestfulApiRespFactory.ok();
    }

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


    @Data
    @Schema(description = "上报视图路由")
    public static class ReportViewRoute extends ApiBaseReq {

        @Schema(description = "视图名称")
        private String name;

        @Schema(description = "视图名称")
        private String pathname;

        @Schema(description = "子路由页面")
        private ReportViewRoute[] routes;
    }
}
