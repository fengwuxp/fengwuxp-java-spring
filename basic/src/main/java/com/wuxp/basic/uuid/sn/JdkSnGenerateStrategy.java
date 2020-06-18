package com.wuxp.basic.uuid.sn;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于jdk的sn生成策略
 *
 * @author wxup
 */
public class JdkSnGenerateStrategy extends AbstractSnGenerateStrategy {

    private Map<String, AtomicLong> atomicLongMap = new ConcurrentReferenceHashMap<>(8);


    @Override
    protected String nextId(String orderPrefix, SnType type) {
        String key = orderPrefix + type.getCode();
        AtomicLong atomicLong = atomicLongMap.putIfAbsent(key, new AtomicLong(0));
        return String.valueOf(atomicLong.incrementAndGet());
    }
}
