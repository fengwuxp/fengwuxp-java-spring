package com.wuxp.basic.uuid;

import java.util.UUID;

public class JdkUUIDGenerateStrategy implements UUIDGenerateStrategy {
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
        if (len < 10) {
            throw new RuntimeException("len is less than 10");
        } else {
            String uuid = UUID.fromString(payload).toString().replaceAll("-", "");
            return uuid.substring(0, len).toUpperCase();
        }
    }
}
