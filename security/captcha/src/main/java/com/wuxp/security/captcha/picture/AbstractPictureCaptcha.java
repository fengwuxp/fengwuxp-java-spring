package com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.CaptchaStore;
import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import com.wuxp.security.captcha.constant.MessageKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class AbstractPictureCaptcha implements PictureCaptcha {


    @Autowired
    protected CaptchaStore captchaStore;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected WuxpCaptchaProperties wuxpCaptchaProperties;




    @Override
    public boolean isEffective(String key) {
        PictureCaptchaValue pictureCaptchaValue = captchaStore.readCaptcha(key, CaptchaType.PICTURE.name());
        if (pictureCaptchaValue == null) {
            return false;
        }
        return !pictureCaptchaValue.isExpired();
    }

    @Override
    public CaptchaVerifyResult verify(String key, CaptchaValue captchaValue) {
        String value = captchaValue.getValue();
        if (!StringUtils.hasText(value)) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_EMPTY,
                            null,
                            "图片验证码不能为空",
                            null));
        }
        String captchaType = captchaValue.getCaptchaType();
        PictureCaptchaValue readCaptcha = captchaStore.readCaptcha(key, captchaType);
        if (readCaptcha == null) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_NOT_EXIST,
                            null,
                            "该图片验证码不存在",
                            null));
        }
        boolean isExpired = readCaptcha.getExpireTime() <= System.currentTimeMillis();
        if (isExpired) {
            captchaStore.removeCaptcha(key, captchaType);
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_NOT_EXIST,
                            null,
                            "该图片验证码不存在",
                            null));
        }
        //不论成功或失败，立即失效
        captchaStore.removeCaptcha(key, captchaType);
        if (value.equalsIgnoreCase(readCaptcha.getValue())) {
            return CaptchaVerifyResult.success();
        }
        return CaptchaVerifyResult.error(
                messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_CHECK_ERROR,
                        null,
                        "图片验证码输入错误",
                        null));
    }



}
