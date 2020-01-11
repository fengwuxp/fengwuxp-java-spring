package com.oak.rbac;

import com.wuxp.security.authority.url.RequestUrlConfigAttribute;
import com.wuxp.security.authority.url.RequestUrlConfigAttributeProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class SimpleRequestUrlConfigAttributeProvider implements RequestUrlConfigAttributeProvider {


    @Override
    public Collection<RequestUrlConfigAttribute> getUrlConfigAttributes(String url) {
        return null;
    }

    @Override
    public Collection<RequestUrlConfigAttribute> getAllUrlConfigAttributes() {
        return null;
    }
}
