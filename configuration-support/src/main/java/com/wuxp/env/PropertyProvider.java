package com.wuxp.env;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * 配置提供者
 *
 * @author wuxp
 */
public interface PropertyProvider<T extends Resource> {


    /**
     * 加载配置
     *
     * @param resource
     * @return
     */
    List<PropertySource<?>> load(T resource);
}
