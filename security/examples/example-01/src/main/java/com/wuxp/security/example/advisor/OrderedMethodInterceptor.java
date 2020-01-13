package com.wuxp.security.example.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

@Slf4j
public class OrderedMethodInterceptor implements MethodBeforeAdvice {

//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//
//        log.info("执行顺序 {}", invocation.getArguments());
//
//        return invocation.proceed();
//    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("执行顺序 {}", args);
    }
}
