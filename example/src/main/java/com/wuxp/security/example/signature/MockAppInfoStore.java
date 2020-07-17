package com.wuxp.security.example.signature;

import com.wuxp.api.signature.AppInfoStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

@Slf4j
public class MockAppInfoStore implements AppInfoStore<MockAppInfo> {


    private static final String appSecret = "com.wuxp.security.example";

    @Override
    @Cacheable(key = "#appId",
            value = APP_STORE_CACHE_NAME,
            condition = "#appId!=null")
    public MockAppInfo getAppInfo(String appId) {
        return new MockAppInfo(appId, appSecret, "mock");
    }
}
