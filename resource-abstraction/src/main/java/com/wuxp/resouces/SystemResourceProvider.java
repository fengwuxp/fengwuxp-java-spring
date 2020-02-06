package com.wuxp.resouces;

import java.util.Collection;

/**
 * resource provider
 */
public interface SystemResourceProvider<ID> {

    Collection<AntUrlResource<ID>> getAntUrlResource(String url);

    Collection<AntUrlResource<ID>> getAllAntUrlResource();

}
