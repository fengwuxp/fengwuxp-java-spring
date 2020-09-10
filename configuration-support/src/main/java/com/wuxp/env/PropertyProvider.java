package com.wuxp.env;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 配置提供者
 *
 * @author wuxp
 */
public interface PropertyProvider<T extends MapPropertySource, R extends Resource> extends PropertyResourceProvider<R>, EnvironmentAware, ApplicationContextAware {


    /**
     * 加载配置
     *
     * @param resource       {{@link #resource()}}
     * @param activeProfiles 激活的Profile {@link org.springframework.core.env.Environment#getActiveProfiles}
     * @return MapPropertySource集合示例
     */
    List<T> load(R resource, String[] activeProfiles);

    /**
     * {@link org.springframework.core.env.PropertySource}
     *
     * @param names {@link org.springframework.core.env.PropertySource#getName()}
     * @return MapPropertySource集合示例
     */
    List<T> loadByName(Map<String,Set<String>> names);

    /**
     * 按照配置的key加载配置
     *
     * @param keys 配置key集合
     * @return {
     *     key: {@link org.springframework.core.env.PropertySource#getName()}
     *     value:[
     *        key：配置key
     *        value：配置值
     *     }
     * }
     */
    Map<String, Map<String, String>> loadKeys(Set<String> keys);

    /**
     * 设置 {@link EnvironmentProviderProperties}
     * @param properties EnvironmentProviderProperties
     */
    void setEnvironmentProviderProperties(EnvironmentProviderProperties properties);
}
