package com.wuxp.security.captcha.picture;

import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import lombok.Getter;

@Getter
public class PictureCaptchaValue implements CaptchaValue {

    private final static CaptchaType CAPTCHA_TYPE = CaptchaType.PICTURE;

    private final String useType;

    private final String value;

    private final long expireTime;

    public PictureCaptchaValue(String value, String useType, long expireTime) {
        this.useType = useType;
        this.value = value;
        this.expireTime = expireTime;
    }

    @Override
    public String getCaptchaType() {
        return CAPTCHA_TYPE.name();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isExpired() {
        return this.getExpireTime() <= System.currentTimeMillis();
    }
}
