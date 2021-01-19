package com.wuxp.security.example.security;

import com.wuxp.resouces.AntUrlResource;
import com.wuxp.security.authority.AntRequestUrlResourceProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * @author wuxp
 */
@Component
public class MockAntRequestUrlResourceProvider implements AntRequestUrlResourceProvider {

    @Override
    public Collection<AntUrlResource<Long>> getAntUrlResource(String url) {
        return null;
    }

    @Override
    public Collection<AntUrlResource<Long>> getAllAntUrlResource() {
        return null;
    }

    @Override
    public Set<String> getUrlResourceAccessRoles(String antPattern) {
        return null;
    }

    @Override
    public Set<String> getUrlResourceAccessAllRoles() {
        return null;
    }
}
