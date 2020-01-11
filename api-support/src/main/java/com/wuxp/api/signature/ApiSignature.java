package com.wuxp.api.signature;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记需要签名的字段
 */
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface ApiSignature {

    /**
     * 签名字段的实际名称, 默认使用字段名称
     *
     * @return
     */
    String name() default "";

    /**
//     * 字段是否参与签名
//     *
//     * @return
//     */
//    String condition() default "#_val==null";
}
