package com.wuxp.security.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * use spring cache store captcha
 */
@Slf4j
public class CacheCaptchaStore implements CaptchaStore {

    public final static String CACHE_CAPTCHA_STORE_KEY = "CAPTCHA_STORE";


    @Autowired
    private CacheManager cacheManager;

    @Override
    public void storeCaptcha(String key, CaptchaValue captchaValue) {
        Cache cache = getCache(captchaValue.getCaptchaType());
        cache.put(key, captchaValue);
    }


    @Override
    public <T extends CaptchaValue> T readCaptcha(String key, String captchaTyp) {
        Cache cache = getCache(captchaTyp);
        CaptchaValue value = cache.get(key, CaptchaValue.class);
        if (value == null) {
            return null;
        }
        return (T) value;


    }


    @Override
    public void removeCaptcha(String key, String captchaTyp) {
        Cache cache = getCache(captchaTyp);
        cache.evict(key);
    }

    private Cache getCache(String captchaTyp) {
        return cacheManager.getCache(captchaTyp + "_" + CACHE_CAPTCHA_STORE_KEY);
    }
}
