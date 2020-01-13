package com.oak.rbac.initiator;

import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过控制器初始化 api url 资源和对应的操作权限
 */
@Slf4j
public class ApiUrlResourceInitiator extends AbstractBaseInitiator<CreateResourceReq> {


    @Autowired
    private ResourceService resourceService;

    private String moduleName = "默认";

    private String moduleCode = "default";


    @Override
    public void init() {

        List<CreateResourceReq> initData = this.getInitData();

        initData.stream().filter(resourceReq -> {
            QueryResourceReq req = new QueryResourceReq();
            req.setQueryType(QueryType.QUERY_NUM);
            req.setId(resourceReq.getCode());
            req.setType(ResourceType.API_URL);
            Pagination<ResourceInfo> permissionInfoPagination = resourceService.queryResource(req);
            return permissionInfoPagination.getTotal() == 0;
        }).forEach(createResourceReq -> {
            resourceService.createResource(createResourceReq);
        });

    }

    @Override
    public List<CreateResourceReq> getInitData() {


        //获取所有的RequestMappingHandlerMapping
        Map<String, RequestMappingHandlerMapping> requestMappingHandlerMappingMap = getAllRequestMappingHandlerMappingMap();
        List<CreateResourceReq> reqs = new ArrayList<>();
        requestMappingHandlerMappingMap.forEach((s, handlerMapping) -> {
            reqs.add(resolveControllerToResource(handlerMapping));
        });

        return reqs;
    }

    private Map<String, RequestMappingHandlerMapping> getAllRequestMappingHandlerMappingMap() {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(
                SpringContextHolder.getApplicationContext(),
                RequestMappingHandlerMapping.class,
                true,
                false);
    }


    private CreateResourceReq resolveControllerToResource(RequestMappingHandlerMapping handlerMapping) {

        CreateResourceReq req = new CreateResourceReq();
        req.setRemark(req.getRemark());
        req.setModuleName(this.moduleName);
        req.setModuleCode(this.moduleCode);

        List<CreatePermissionReq> permissionReqs = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
            HandlerMethod handlerMethod = requestMappingInfoHandlerMethodEntry.getValue();
            this.setCreateResourceReq(req, handlerMethod);
            CreatePermissionReq permissionReq = resolveControllerToPermission(requestMappingInfo, handlerMethod);
            if (permissionReq != null) {
                permissionReqs.add(permissionReq);
            }

        }

        req.setPermissions(permissionReqs.toArray(new CreatePermissionReq[0]));

        return req;
    }

    private void setCreateResourceReq(CreateResourceReq req, HandlerMethod handlerMethod) {
        if (StringUtils.hasText(req.getName())) {
            return;
        }

        Class<?> aClass = handlerMethod.getBeanType();
        Tag tag = aClass.getAnnotation(Tag.class);
        if (tag == null) {
            return;
        }
        req.setName(tag.name());
        req.setRemark(tag.description());
    }

    private CreatePermissionReq resolveControllerToPermission(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {
        Operation methodAnnotation = handlerMethod.getMethodAnnotation(Operation.class);
        if (methodAnnotation == null) {
            return null;
        }

        String[] patterns = mappingInfo.getPatternsCondition().getPatterns().toArray(new String[0]);
        String uri = patterns[0];
        String[] values = uri.split("/");

        CreatePermissionReq req = new CreatePermissionReq();
        req.setResourceId(values[0]);
        req.setValue(uri);
        req.setName(methodAnnotation.summary());
        req.setRemark(methodAnnotation.description());

        return req;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}
