package com.wuxp.security.captcha.qrcode;

import com.google.zxing.client.j2se.MatrixToImageConfig;

import java.awt.*;

/**
 * 二维码生成
 */
public interface QrCodeCaptchaGenerator {


    /**
     * @param content
     * @param width
     * @param margin
     * @return
     */
    default Image generate(String content, int width, int margin) {
        return generate(content, width, margin, new MatrixToImageConfig());
    }


    /**
     * @param content
     * @param width
     * @param margin
     * @param config
     * @return
     */
    default Image generate(String content, int width, int margin, MatrixToImageConfig config) {
        return generate(content, width, margin, null, 0, 0, null, config);
    }


    /**
     * @param content
     * @param width
     * @param margin
     * @param logo
     * @param logoWidth
     * @param logoBorderWith
     * @param logoBorderColor rgb value example: #ff0000
     * @return
     */
    default Image generate(String content,
                           int width,
                           int margin,
                           String logo,
                           int logoWidth,
                           int logoBorderWith,
                           String logoBorderColor) {
        return generate(content, width, margin, logo, logoWidth, logoBorderWith, logoBorderColor, new MatrixToImageConfig());
    }

    /**
     * @param content
     * @param width
     * @param margin
     * @param logo
     * @param logoWidth
     * @param logoBorderWith
     * @param logoBorderColor rgb value example: #ff0000
     * @param config
     * @return
     */
    Image generate(String content,
                   int width,
                   int margin,
                   String logo,
                   int logoWidth,
                   int logoBorderWith,
                   String logoBorderColor,
                   MatrixToImageConfig config);
}
