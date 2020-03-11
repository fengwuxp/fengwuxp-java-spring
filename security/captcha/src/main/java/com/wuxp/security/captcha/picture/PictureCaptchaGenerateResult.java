package com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.CaptchaGenerateResult;
import com.wuxp.security.captcha.CaptchaType;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * picture captcha
 */
@Getter
public class PictureCaptchaGenerateResult implements CaptchaGenerateResult {

    private final String key;

    private final PictureCaptchaValue value;

    // 验证吗内容
    private String captchaContent;

    private String errorMessage;

    private PictureCaptchaGenerateResult(String useType, String key, String value, long expireTime) {
        PictureCaptchaValue pictureCaptchaValue = new PictureCaptchaValue(value, useType, expireTime);
        this.key = key;
        this.value = pictureCaptchaValue;
        this.captchaContent = value;
    }

    @Override
    public boolean isSuccess() {
        return this.captchaContent != null;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCaptchaContent(String captchaContent) {
        this.captchaContent = captchaContent;
    }


    public static PictureCaptchaGenerateResult newInstance(String type, long expireTime) {
        return newInstance(type, 4, expireTime);
    }

    public static PictureCaptchaGenerateResult newInstance(String useType, int len, long expireTime) {
        return newInstance(useType,
                null,
                RandomStringUtils.randomAlphanumeric(len),
                expireTime);
    }

    public static PictureCaptchaGenerateResult newInstance(String useType, String key, String value, long expireTime) {
        if (!StringUtils.hasText(key)) {
            key = MessageFormat.format("{0}_{1}_{2}", CaptchaType.PICTURE.name(), useType, UUID.randomUUID());
        }
        return new PictureCaptchaGenerateResult(useType, key, value, expireTime);
    }
}
