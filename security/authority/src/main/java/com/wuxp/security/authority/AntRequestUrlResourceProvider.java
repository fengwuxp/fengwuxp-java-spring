package com.wuxp.security.authority;

import com.wuxp.resouces.SystemResourceProvider;

import java.util.Set;

/**
 * url 属性提供者
 */
public interface AntRequestUrlResourceProvider extends SystemResourceProvider<Long> {



    /**
     * 获取允许访问资源的角色
     *
     * @param antPattern ant 风格的表达式
     * @return
     */
    Set<String> getUrlResourceAccessRoles(String antPattern);

    /**
     * 获取所有的 url资源访问的角色
     */
    Set<String> getUrlResourceAccessAllRoles();
}
