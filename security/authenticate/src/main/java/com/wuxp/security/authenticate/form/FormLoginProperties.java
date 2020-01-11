package com.wuxp.security.authenticate.form;

import lombok.Data;

/**
 * form login 配置
 */
@Data
public class FormLoginProperties {


    /**
     * 登录页面
     */
    private String loginPage = "/login";

    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/do_login";

    /**
     * 在登录失败多少次后需要验证码验证
     */
    private Integer showCaptchaByFailureCount = 2;
}
