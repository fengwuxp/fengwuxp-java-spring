package com.wuxp.api.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wxup
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface ApiLog {

    /**
     * support spel
     *
     * <p>
     *
     *  #result
     *  #authorization
     *  #url
     *  #ip
     *
     * </p>
     *  {@code #result} 从方法结果获取
     * @return
     */
    String value() default "";

    /**
     * api log type
     *
     * @return
     */
    String type() default "";

    /**
     * api log action
     *
     * @return
     */
    String action() default "";


}
