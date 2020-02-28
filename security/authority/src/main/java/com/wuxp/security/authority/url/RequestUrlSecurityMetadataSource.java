package com.wuxp.security.authority.url;

import com.wuxp.resouces.AntUrlResource;
import com.wuxp.security.authority.AntRequestUrlResourceProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 从请求url中获去到权限相关的数据
 */
@Slf4j
@Data
public class RequestUrlSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, BeanFactoryAware, InitializingBean {


    private AntRequestUrlResourceProvider antRequestUrlResourceProvider;

    private BeanFactory beanFactory;

    public RequestUrlSecurityMetadataSource() {
    }

    public RequestUrlSecurityMetadataSource(AntRequestUrlResourceProvider antRequestUrlResourceProvider) {
        this.antRequestUrlResourceProvider = antRequestUrlResourceProvider;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取当前的请求
        final FilterInvocation filterInvocation = (FilterInvocation) object;
        final HttpServletRequest request = filterInvocation.getRequest();
        // 获取请求地址
        String requestUrl = filterInvocation.getRequestUrl();
        Collection<AntUrlResource<Long>> urlConfigAttributes = antRequestUrlResourceProvider.getAntUrlResource(requestUrl);
        Set<RequestMatcher> requestMatchers = urlConfigAttributes.stream()
                .map(antUrlResource -> new AntPathRequestMatcher(antUrlResource.getPattern(), antUrlResource.getHttpMethod()))
                .collect(Collectors.toSet());

        // 拿到其中一个  没有就算非法访问
        RequestMatcher reqMatcher = requestMatchers.stream()
                .filter(requestMatcher -> requestMatcher.matches(request))
                .findAny().orElseThrow(() -> new AccessDeniedException("非法访问"));

        if (urlConfigAttributes.isEmpty()) {
            // 请求路径没有配置权限，表明该请求接口可以任意访问
            return Collections.emptyList();
        }
        AntPathRequestMatcher antPathRequestMatcher = (AntPathRequestMatcher) reqMatcher;
        Set<String> resourceAccessRoles = antRequestUrlResourceProvider.getUrlResourceAccessRoles(antPathRequestMatcher.getPattern());

        String[] authorities = resourceAccessRoles.toArray(new String[0]);
        return SecurityConfig.createList(authorities);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<String> allRoles = antRequestUrlResourceProvider.getUrlResourceAccessAllRoles();
        String[] authorities = allRoles.toArray(new String[0]);
        return SecurityConfig.createList(authorities);


    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.antRequestUrlResourceProvider == null) {
            this.antRequestUrlResourceProvider = this.beanFactory.getBean(AntRequestUrlResourceProvider.class);
        }
    }
}
