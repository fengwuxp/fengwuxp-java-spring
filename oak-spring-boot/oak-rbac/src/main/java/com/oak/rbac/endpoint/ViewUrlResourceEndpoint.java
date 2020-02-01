package com.oak.rbac.endpoint;


import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.PermissionValueType;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.menu.MenuService;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    public ApiResp<Void> reportViewRoutes(@RequestBody ReportViewRoute[] routes) {


        // 分组
        Map<String, List<ReportViewRoute>> viewRouteGroup = new HashMap<>();
        Arrays.stream(routes).forEach(reportViewRoute -> {
            String pathname = reportViewRoute.getPathname();
            String[] values = pathname.split("/");
            String resourceCode = values[1];
            List<ReportViewRoute> viewRoutes = viewRouteGroup.computeIfAbsent(resourceCode, k -> new ArrayList<>());
            viewRoutes.add(reportViewRoute);
        });

        List<CreateResourceReq> reqs = new ArrayList<>(viewRouteGroup.size());
        viewRouteGroup.forEach((key, viewRoutes) -> {
            CreateResourceReq req = new CreateResourceReq();
            req.setCode(key);
            req.setType(ResourceType.URL);
            CreatePermissionReq[] permissionReqs = viewRoutes.stream().map(reportViewRoute -> {
                CreatePermissionReq permissionReq = new CreatePermissionReq();
                permissionReq.setName(reportViewRoute.getName());
                permissionReq.setValue(reportViewRoute.getPathname());
                permissionReq.setValueType(PermissionValueType.VIEW);
                permissionReq.setResourceId(key);
                return permissionReq;
            }).toArray(CreatePermissionReq[]::new);
            req.setPermissions(permissionReqs);
            reqs.add(req);
        });

        return this.reportViewResource(reqs.toArray(new CreateResourceReq[0]));
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
                    req.setType(ResourceType.URL);
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

//        private String resourceName;

//        @Schema(description = "子路由页面")
//        private ReportViewRoute[] routes;
    }
}
