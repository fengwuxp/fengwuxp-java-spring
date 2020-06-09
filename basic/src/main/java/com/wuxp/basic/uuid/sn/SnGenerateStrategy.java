package com.wuxp.basic.uuid.sn;

import com.wuxp.basic.uuid.UUIDGenerateStrategy;

/**
 * sn生成策略，生成固定长度的订单号
 * {@link #uuid(int)}
 * {@link #uuid(int, String)} 都使用{@link #uuid}
 * @author wxup
 */
public interface SnGenerateStrategy extends UUIDGenerateStrategy {


    @Override
    default String uuid(int len) {
        return uuid();
    }

    @Override
    default String uuid(int len, String payload) {
        return uuid();
    }
}
