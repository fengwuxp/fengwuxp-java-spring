package com.wuxp.security.captcha.mobile;

import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import lombok.Getter;

@Getter
public class MobileCaptchaValue implements CaptchaValue {

    private final static CaptchaType CAPTCHA_TYPE = CaptchaType.MOBILE;

    private final String useType;

    private final String value;

    private int failureVerifyCount;

    private final long expireTime;

    public MobileCaptchaValue(String value, String useType, long expireTime) {
        this.value = value;
        this.useType = useType;
        this.failureVerifyCount = 0;
        this.expireTime = expireTime;
    }

    @Override
    public String getCaptchaType() {
        return CAPTCHA_TYPE.name();
    }


    public void addFailureCount() {
        this.failureVerifyCount++;
    }

    @Override
    public boolean isExpired() {
        return this.getExpireTime() <= System.currentTimeMillis();
    }
}
