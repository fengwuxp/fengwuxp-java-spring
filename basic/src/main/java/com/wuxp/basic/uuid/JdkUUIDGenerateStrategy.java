package com.wuxp.basic.uuid;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 基于jdk的uuid生成策略
 *
 * @author wxup
 */
@Slf4j
public class JdkUUIDGenerateStrategy implements UUIDGenerateStrategy {

    private static final int MIN_LENGTH = 10;

    public JdkUUIDGenerateStrategy() {
    }

    @Override
    public String uuid() {
        return this.uuid(32, UUID.randomUUID().toString());
    }

    @Override
    public String uuid(int len) {
        return this.uuid(len, UUID.randomUUID().toString());
    }

    @Override
    public String uuid(int len, String payload) {
        if (len < MIN_LENGTH) {
            throw new RuntimeException("len is less than :" + MIN_LENGTH);
        } else {
            String uuid = UUID.fromString(payload).toString().replaceAll("-", "");
            return uuid.substring(0, len).toUpperCase();
        }
    }


}
