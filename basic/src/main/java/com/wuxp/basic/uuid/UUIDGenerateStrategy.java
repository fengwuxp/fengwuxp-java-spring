package com.wuxp.basic.uuid;

/**
 * uuid 生成策略
 */
public interface UUIDGenerateStrategy {

    String uuid();

    String uuid(int len);

    String uuid(int len, String payload);
}
