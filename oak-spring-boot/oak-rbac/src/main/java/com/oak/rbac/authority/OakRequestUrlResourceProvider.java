package com.oak.rbac.authority;

import com.oak.rbac.enums.PermissionValueType;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.wuxp.api.model.Pagination;
import com.wuxp.resouces.AntUrlResource;
import com.wuxp.security.authority.AntRequestUrlResourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 加载 url资源
 */
@Slf4j
public class OakRequestUrlResourceProvider implements AntRequestUrlResourceProvider {

    public static final String URL_ACCESS_ROLES_CACHE_NAME = "URL_ACCESS_ROLES_CACHE";

    public static final String ROOT_ROLE = "ROOT_ROLE";

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    @Cacheable(value = {URL_ACCESS_ROLES_CACHE_NAME},
            key = "#antPattern",
            condition = "#antPattern!=null",
            unless = "#result.empty")
    public Set<String> getUrlResourceAccessRoles(String antPattern) {

        QueryPermissionReq req = new QueryPermissionReq();
        req.setValue(antPattern);
        req.setValueType(PermissionValueType.API);
        req.setFetchRole(true);
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(req);

        Set<String> roles = pagination.getRecords().stream()
                .map(PermissionInfo::getRoles)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(RoleInfo::getName)
                .collect(Collectors.toSet());
        roles.add(ROOT_ROLE);
        return roles;

    }

    @Override
    public Set<String> getUrlResourceAccessAllRoles() {
        QueryRoleReq req = new QueryRoleReq();
        req.setEnable(true);
        Pagination<RoleInfo> roleInfoPagination = roleService.queryRole(req);
        Set<String> roles = roleInfoPagination.getRecords().stream()
                .map(RoleInfo::getName)
                .collect(Collectors.toSet());
        roles.add(ROOT_ROLE);
        return roles;
    }

    @Override
    public Collection<AntUrlResource<Long>> getAntUrlResource(String url) {
        QueryPermissionReq req = new QueryPermissionReq();
        req.setValue(url);
        req.setValueType(PermissionValueType.API);
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(req);
        List<PermissionInfo> records = pagination.getRecords();
        return records.stream().map(permissionInfo -> (AntUrlResource<Long>) permissionInfo).collect(Collectors.toList());
    }

    @Override
    public Collection<AntUrlResource<Long>> getAllAntUrlResource() {
        QueryPermissionReq req = new QueryPermissionReq();
        req.setValueType(PermissionValueType.API);
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(req);
        List<PermissionInfo> records = pagination.getRecords();
        return records.stream().map(permissionInfo -> (AntUrlResource<Long>) permissionInfo).collect(Collectors.toList());
    }
}
