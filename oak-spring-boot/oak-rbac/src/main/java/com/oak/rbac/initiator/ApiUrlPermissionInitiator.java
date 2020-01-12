package com.oak.rbac.initiator;

import com.oak.rbac.enums.PermissionType;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通过控制器初始化 url权限列表
 */
@Slf4j
public class ApiUrlPermissionInitiator extends AbstractBaseInitiator<CreatePermissionReq> {


    @Autowired
    private PermissionService permissionService;


    @Override
    public void init() {

        List<CreatePermissionReq> initData = this.getInitData();

        initData.stream().filter(createPermissionReq -> {
            QueryPermissionReq req = new QueryPermissionReq();
            req.setCode(createPermissionReq.getCode());
            req.setQueryType(QueryType.QUERY_NUM);
            Pagination<PermissionInfo> permissionInfoPagination = permissionService.queryPermission(req);
            return permissionInfoPagination.getTotal() == 0;
        }).forEach(createPermissionReq -> {
            permissionService.createPermission(createPermissionReq);
        });

    }

    @Override
    public List<CreatePermissionReq> getInitData() {


        //获取所有的RequestMappingHandlerMapping
        Map<String, RequestMappingHandlerMapping> requestMappingHandlerMappingMap = getAllRequestMappingHandlerMappingMap();
        List<CreatePermissionReq> reqs = new ArrayList<>();
        requestMappingHandlerMappingMap.forEach((s, handlerMapping) -> {

            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                HandlerMethod handlerMethod = requestMappingInfoHandlerMethodEntry.getValue();
                resolveControllerToReq(requestMappingInfo, handlerMethod);
            }
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


    private CreatePermissionReq resolveControllerToReq(RequestMappingInfo mappingInfo, HandlerMethod handlerMethod) {

        String[] patterns = mappingInfo.getPatternsCondition().getPatterns().toArray(new String[0]);
        String uri = patterns[0];
        String[] values = uri.split("/");
        // example : /test/value ==> TEST_VALUE
        String code = uri.substring(1).replaceAll("/", "_").toUpperCase();

        CreatePermissionReq req = new CreatePermissionReq();
        req.setResourceId(values[0]);
        req.setType(PermissionType.API);
        req.setValue(uri);
        req.setCode(code);
        Operation methodAnnotation = handlerMethod.getMethodAnnotation(Operation.class);
        if (methodAnnotation == null) {
            req.setName(handlerMethod.getMethod().getName());
        } else {
            req.setName(methodAnnotation.summary());
            req.setRemark(methodAnnotation.description());
        }
        return req;
    }

}
