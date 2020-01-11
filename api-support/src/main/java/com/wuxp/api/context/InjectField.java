package com.wuxp.api.context;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 注入字段值
 */
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface InjectField {


    /**
     * 注入字段的spel表达式
     * {@link ApiRequestContext}
     *
     * @return
     */
    String value() default "";

//    @AliasFor("value")
//    String expression() default "";

    /**
     * 是否注入字段的条件
     *
     * @return
     */
    String condition() default "#_val==null";
}
