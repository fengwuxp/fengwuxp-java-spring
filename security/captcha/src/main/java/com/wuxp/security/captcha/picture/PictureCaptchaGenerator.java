package com.wuxp.security.captcha.picture;

import java.awt.*;

/**
 * picture captcha generator
 */
public interface PictureCaptchaGenerator {

    /**
     * generate picture
     * @param content
     * @param width
     * @param height
     * @param gif use gif
     * @return
     */
    Image generate(String content, int width, int height,boolean gif);

    /***
     *  get picture background color
     * @return
     */
    Color backgroundColor();

    /**
     * get  captcha text color
     *
     * @return
     */
    Color captchaTextColor();

    /**
     * get interference line color
     *
     * @return
     */
    Color interferenceLineColor();

    /**
     * get bezier curve color
     * @return
     */
    Color bezierCurveColor();


    /**
     * get captcha text font
     * @return
     */
    Font captchaTextFont(String text);
}
