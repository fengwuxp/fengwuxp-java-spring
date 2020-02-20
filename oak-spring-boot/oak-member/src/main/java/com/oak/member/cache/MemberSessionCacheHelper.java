package com.oak.member.cache;

import com.oak.member.management.member.info.MemberLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author laiy
 * create at 2020-02-18 17:43
 * @Description
 */
@Slf4j
@Component
public class MemberSessionCacheHelper {

    public static final String OAK_MEMBER_LOGIN_INFO_CACHE = "OAK_MEMBER_LOGIN_INFO_CACHE";


    @Cacheable(value = {OAK_MEMBER_LOGIN_INFO_CACHE}, key = "#token", condition = "#token!=null")
    public MemberLoginInfo get(String token) {
        return null;
    }

    @CachePut(value = {OAK_MEMBER_LOGIN_INFO_CACHE}, key = "#token", condition = "#token!=null")
    public MemberLoginInfo join(String token, MemberLoginInfo loginInfo) {
        return loginInfo;
    }

    @CacheEvict(value = {OAK_MEMBER_LOGIN_INFO_CACHE}, key = "#token", condition = "#token!=null")
    public void remove(String token) {

    }

}
