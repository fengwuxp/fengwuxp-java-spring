package com.wuxp.api.signature;

import javax.validation.constraints.NotNull;

/**
 * app info store
 * @author wxup
 */
public interface AppInfoStore {

    String APP_STORE_CACHE_NAME = "APP_INFO_STORE";

    /**
     * 获取app
     *
     * @param appId
     * @param <T>
     * @return
     */
    <T extends AppInfo> T getAppInfo(@NotNull String appId);
}
