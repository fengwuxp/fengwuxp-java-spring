package com.wuxp.api.context;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 注入字段值
 *
 * @author wxup
 */
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface InjectField {


    /**
     * 强制注入条件
     */
    String FORCE_INJECT = "true";

    /**
     * 注入字段的spel表达式
     *
     * @return
     * @see InjectFieldExpressionConstant
     */
    String value() default "";


    /**
     * 是否注入字段的条件
     * 默认情况下，被注解标记的字段值为null是注入
     * @return
     */
    String condition() default "#_val==null";
}
