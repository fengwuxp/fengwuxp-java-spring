package com.wuxp.env.refresh;

/**
 * 刷新环境变量的范围
 *
 * @author wuxp
 */
public enum EnvironmentRefreshScope {

    /**
     * 所有的环境
     */
    ALL,

    /**
     * 由{@link com.wuxp.env.PropertyProvider}加载的所有的配置
     */
    PROVIDER_ALL,

    /**
     * 由{@link com.wuxp.env.PropertyProvider}加载的所有的配置
     * 按照 {@link org.springframework.core.env.PropertySource#getName}进行刷新
     */
    PROVIDER_NAME,


    /**
     * 由{@link com.wuxp.env.PropertyProvider}加载的所有的配置
     * 且按照配置key刷新
     */
    PROVIDER_KEY,

    /**
     * 由{@link com.wuxp.env.PropertyProvider}加载的所有的配置
     * 且按照配置key的前缀刷新
     */
    PROVIDER_PREFIX


}
