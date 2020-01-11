//package com.wuxp.security.example.advisor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Slf4j
//public class OrderedAspect {
//
//
//    @Pointcut("@annotation(com.wuxp.api.log.ApiLog)")
//    private void log() {
//
//    }
//
//
//    @Before("log()")
//    public void before() {
//        log.info("-------->");
//    }
//
//}
