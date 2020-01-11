package com.wuxp.security.captcha.configuration;

import lombok.Data;

import java.time.Duration;

/**
 * mobile captcha properties
 */
@Data
public class MobileCaptchaProperties {


    /**
     * 验证码长度
     */
    private int length = 4;

    /**
     * 有效时长
     */
    private Duration expired = Duration.ofSeconds(900);

    /**
     * 最大可以验证次数
     */
    private int maxReties = 5;

    public MobileCaptchaProperties() {
    }


}
