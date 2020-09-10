package com.wuxp.env;

import org.springframework.core.io.Resource;

/**
 * PropertyResource 提供者
 *
 * @param <T> PropertyResource
 * @author wuxp
 */
public interface PropertyResourceProvider<T extends Resource> {

    /**
     * 获取一个用于加载配置的{@link Resource}
     *
     * @return T
     */
    T resource();

    /**
     * 刷新 {@link T}，在接收到 {@link  org.springframework.boot.context.event.ApplicationStartedEvent}事件后执行
     * 有且只能执行一次
     */
    void refresh();
}
