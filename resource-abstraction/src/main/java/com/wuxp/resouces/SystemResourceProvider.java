package com.wuxp.resouces;

import java.util.Collection;

/**
 * resource provider
 *
 * @author wxup
 */
public interface SystemResourceProvider<ID> {

    /**
     * 根据获取url资源
     *
     * @param url
     * @return url资源列表
     */
    Collection<AntUrlResource<ID>> getAntUrlResource(String url);

    /**
     * 获取所有的url资源
     *
     * @return url资源列表
     */
    Collection<AntUrlResource<ID>> getAllAntUrlResource();

}
