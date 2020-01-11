package com.wuxp.security.captcha.mobile;

import com.wuxp.security.captcha.CaptchaGenerateResult;
import com.wuxp.security.captcha.CaptchaType;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.MessageFormat;


@Getter
public class MobileCaptchaGenerateResult implements CaptchaGenerateResult {

    private final String key;

    private final MobileCaptchaValue value;

    private String errorMessage;

    private MobileCaptchaGenerateResult(String useType, String key, String value, long expireTime) {
        MobileCaptchaValue mobileCaptchaValue = new MobileCaptchaValue(value, useType, expireTime);
        this.key = key;
        this.value = mobileCaptchaValue;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean isSuccess() {
        return errorMessage == null;
    }


    public static MobileCaptchaGenerateResult newInstance(String useType, String mobilePhone, long expireTime) {
        return newInstance(useType, mobilePhone, 4, expireTime);
    }

    public static MobileCaptchaGenerateResult newInstance(String useType, String mobilePhone, int len, long expireTime) {
        return newInstance(useType,
                MessageFormat.format("{0}_{1}_{2}", CaptchaType.MOBILE.name(), useType, mobilePhone),
                RandomStringUtils.randomNumeric(len),
                expireTime);
    }

    public static MobileCaptchaGenerateResult newInstance(String useType, String key, String value, long expireTime) {
        return new MobileCaptchaGenerateResult(useType, key, value, expireTime);
    }

}
