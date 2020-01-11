package com.wuxp.security.authority.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 从请求url中获去到权限相关的数据
 */
@Slf4j
public class RequestUrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    private RequestUrlConfigAttributeProvider requestUrlConfigAttributeProvider;

    public RequestUrlSecurityMetadataSource(RequestUrlConfigAttributeProvider requestUrlConfigAttributeProvider) {
        this.requestUrlConfigAttributeProvider = requestUrlConfigAttributeProvider;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        Collection<RequestUrlConfigAttribute> urlConfigAttributes = requestUrlConfigAttributeProvider.getUrlConfigAttributes(requestUrl);
        if (urlConfigAttributes == null || urlConfigAttributes.isEmpty()) {
            // 请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        return urlConfigAttributes.stream()
                .map(requestUrlConfigAttribute -> (ConfigAttribute) requestUrlConfigAttribute)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return requestUrlConfigAttributeProvider.getAllUrlConfigAttributes()
                .stream().map(requestUrlConfigAttribute -> (ConfigAttribute) requestUrlConfigAttribute)
                .collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
