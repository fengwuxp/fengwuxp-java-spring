package com.wuxp.api.signature;

import javax.validation.constraints.NotNull;

/**
 * app info store
 *
 * @author wxup
 */
public interface AppInfoStore<T extends AppInfo> {

    String APP_STORE_CACHE_NAME = "APP_INFO_STORE";

    /**
     * 获取app
     *
     * @param appId 应用app id
     * @return 应用信息
     */
    T getAppInfo(@NotNull String appId);
}
