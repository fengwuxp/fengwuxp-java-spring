package com.wuxp.security.authenticate.mobile;

import lombok.Data;

/**
 * 短信验证码登录
 * @author wxup
 */
@Data
public class MobileCaptchaLoginProperties {

    /**
     * 登录处理url
     */
    private String loginProcessingUrl = "/mobile_captcha/login";

}
