package com.wuxp.resouces;

import java.util.Collection;

/**
 * resource provider
 *
 * @author wxup
 */
public interface SystemResourceProvider<ID> {

    Collection<AntUrlResource<ID>> getAntUrlResource(String url);

    Collection<AntUrlResource<ID>> getAllAntUrlResource();

}
