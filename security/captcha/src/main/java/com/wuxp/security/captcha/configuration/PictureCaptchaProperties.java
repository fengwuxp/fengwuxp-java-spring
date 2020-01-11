package com.wuxp.security.captcha.configuration;

import lombok.Data;

import java.time.Duration;

/**
 * picture captcha properties
 */
@Data
public class PictureCaptchaProperties {


    /**
     * 图片验证码长度
     */
    private int length = 4;

    /**
     * 图片验证码宽度
     */
    private int width = 160;

    /**
     * 图片验证码高度
     */
    private int height = 50;

    /**
     * 有效时长
     */
    private Duration expired = Duration.ofSeconds(600);

    /**
     * 使用gif 格式
     */
    private boolean gif = false;

    public PictureCaptchaProperties() {
    }

    public PictureCaptchaProperties(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
