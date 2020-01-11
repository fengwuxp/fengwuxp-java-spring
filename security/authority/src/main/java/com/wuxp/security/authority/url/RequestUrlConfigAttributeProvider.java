package com.wuxp.security.authority.url;

import java.util.Collection;
import java.util.List;

/**
 * url 属性提供者
 */
public interface RequestUrlConfigAttributeProvider {

    Collection<RequestUrlConfigAttribute> getUrlConfigAttributes(String url);

    Collection<RequestUrlConfigAttribute> getAllUrlConfigAttributes();
}
