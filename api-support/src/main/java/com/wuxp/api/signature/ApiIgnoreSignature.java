package com.wuxp.api.signature;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记不需要拦截处理的方法或控制器
 *
 * @author wxup
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface ApiIgnoreSignature {

    String value() default "";
}
