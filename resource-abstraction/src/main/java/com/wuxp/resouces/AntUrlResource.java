package com.wuxp.resouces;


import com.wuxp.resouces.enums.ResourceType;

/**
 * ant url 资源
 * @author wuxp
 */
public interface AntUrlResource<ID> extends SystemResource<ID> {


    @Override
    default ResourceType getResourceType() {
        return ResourceType.URL;
    }

    /**
     * 获取匹配的 ant表达式
     *
     * @return
     * @see org.springframework.util.AntPathMatcher
     */
    String getPattern();

    /**
     * 获取请求需要的 http method
     *
     * @return
     * @see org.springframework.http.HttpMethod
     */
    String getHttpMethod();

}
