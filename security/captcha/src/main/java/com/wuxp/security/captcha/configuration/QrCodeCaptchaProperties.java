package com.wuxp.security.captcha.configuration;

import lombok.Data;

import java.time.Duration;


/**
 * qrcode captcha
 */
@Data
public class QrCodeCaptchaProperties {

    private int width = 200;

    private int margin = 1;

    private String logo;

    private int logoWidth = 40;

    private int logoBorderWith = 1;

    private String logoBorderColor = "#ffffff";

    /**
     * 有效时长
     */
    private Duration expired = Duration.ofSeconds(180);


}
