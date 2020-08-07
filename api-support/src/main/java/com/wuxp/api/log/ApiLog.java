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
     * 支持模板字符串 {@link com.wuxp.api.log.TemplateExpressionParser}
     * <p>
     * <p>
     * #result
     * #authorization
     * #url
     * #ip
     *
     * </p>
     * {@code #result} 从方法结果获取
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


    /**
     * support spel
     * 操作的目标资源id
     *
     * @return
     */
    String targetResourceId() default "";


}
