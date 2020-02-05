package com.oak.rbac.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 用户会话缓存
 */
@Slf4j
@Component
public class UserSessionCacheHelper {

    public static final String OAK_ADMIN_USER_CACHE_NAME = "OAK_ADMIN_USER_CACHE";


    @Cacheable(value = {OAK_ADMIN_USER_CACHE_NAME}, key = "#token", condition = "#token!=null")
    public OakUser get(String token) {
        return null;
    }

    @CachePut(value = {OAK_ADMIN_USER_CACHE_NAME}, key = "#token", condition = "#token!=null")
    public OakUser join(String token, OakUser userDetails) {
        return userDetails;
    }

    @CacheEvict(value = {OAK_ADMIN_USER_CACHE_NAME}, key = "#token", condition = "#token!=null")
    public void remove(String token) {

    }
}
