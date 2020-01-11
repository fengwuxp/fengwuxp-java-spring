package com.wuxp.api.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
     *
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
