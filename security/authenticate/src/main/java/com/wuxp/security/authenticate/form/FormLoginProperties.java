package com.wuxp.security.authenticate.form;

import lombok.Data;

/**
 * form login 配置
 *
 * @author wxup
 */
@Data
public class FormLoginProperties {


    /**
     * 登录页面
     */
    private String loginPage = "/form/login";

    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/form/login";

    /**
     * 在登录失败多少次后需要验证码验证
     */
    private Integer showCaptchaByFailureCount = 3;
}
