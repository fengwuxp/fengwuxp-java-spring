package com.wuxp.security.captcha;

import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import com.wuxp.security.captcha.picture.PictureCaptchaValue;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CombinationCaptcha implements Captcha {

    private Map<CaptchaType, Captcha> captchaMap;

    @Autowired
    private WuxpCaptchaProperties wuxpCaptchaProperties;

    public CombinationCaptcha(List<Captcha> captchaList) {
        Map<CaptchaType, Captcha> captchaMap = new HashMap<>(captchaList.size());
        captchaList.forEach(captcha -> {
            captchaMap.put(captcha.getCaptchaType(), captcha);
        });
        this.captchaMap = captchaMap;
    }

    @Override
    public CaptchaType getCaptchaType() {
        return null;
    }

    @Override
    public CaptchaGenerateResult generate(String useType, String key) {

        return this.getCaptchaByKey(key).generate(useType, key);
    }

    @Override
    public boolean isEffective(String key) {
        return this.getCaptchaByKey(key).isEffective(key);
    }

    @Override
    public CaptchaVerifyResult verify(String key, CaptchaValue captchaValue) {
        return this.getCaptchaByKey(key).verify(key, captchaValue);
    }

    public CaptchaValue genCaptchaValue(String key, String value) {
        String[] keys = key.split("_");
        CaptchaType captchaType = CaptchaType.valueOf(keys[0]);
        if (CaptchaType.MOBILE.equals(captchaType)) {
            return new MobileCaptchaValue(value, keys[1], System.currentTimeMillis() + wuxpCaptchaProperties.getMobileCaptchaProperties(captchaType.name()).getExpired().toMillis());
        } else if (CaptchaType.PICTURE.equals(captchaType)) {
            return new PictureCaptchaValue(value, keys[1], System.currentTimeMillis() + wuxpCaptchaProperties.getPictureProperties(captchaType.name()).getExpired().toMillis());
        } else if (CaptchaType.QR_CODE.equals(captchaType)) {
            return new QrCodeCaptchaValue(value, keys[1], System.currentTimeMillis() + wuxpCaptchaProperties.getQrCodeCaptchaProperties(captchaType.name()).getExpired().toMillis());
        }
        return null;
    }

    private Captcha getCaptchaByKey(String key) {
        CaptchaType captchaType = CaptchaType.valueOf(key.split("_")[0]);
        return captchaMap.get(captchaType);
    }
}
