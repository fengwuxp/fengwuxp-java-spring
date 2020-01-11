package com.wuxp.api.interceptor;


import java.lang.reflect.Method;

/**
 * api操作的源
 * 参数注入
 * 签名验证
 * 日志记录
 */
public interface ApiOperationSource {


    /**
     * 确定给定类和方否符合接口需要的操作
     *
     * @param method
     * @param targetClass
     * @return
     */
    default boolean isCandidateClass(Method method, Class<?> targetClass) {
        return true;
    }
}
