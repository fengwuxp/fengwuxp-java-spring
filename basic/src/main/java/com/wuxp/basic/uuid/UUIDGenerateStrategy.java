package com.wuxp.basic.uuid;

/**
 * uuid 生成策略
 *
 * @author wxup
 */
public interface UUIDGenerateStrategy {

    /**
     * 生成uuId
     *
     * @return
     */
    String uuid();

    /**
     * 生成指定长度的uuid
     *
     * @param len uui长度
     * @return
     */
    String uuid(int len);

    String uuid(int len, String payload);
}
