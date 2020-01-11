package com.wuxp.security.captcha.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.wuxp.security.captcha.CacheCaptchaStore;
import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CaptchaStore;
import com.wuxp.security.captcha.CombinationCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaSender;
import com.wuxp.security.captcha.mobile.SimpleMobileCaptcha;
import com.wuxp.security.captcha.picture.DefaultPictureGenerator;
import com.wuxp.security.captcha.picture.PictureCaptcha;
import com.wuxp.security.captcha.picture.PictureCaptchaGenerator;
import com.wuxp.security.captcha.picture.SimplePictureCaptcha;
import com.wuxp.security.captcha.qrcode.DefaultQrCodeCaptchaGenerator;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaGenerator;
import com.wuxp.security.captcha.qrcode.SimpleQrCodeCaptcha;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

@Configuration
@EnableConfigurationProperties(value = {WuxpCaptchaProperties.class})
@ConditionalOnClass({Captcha.class})
@ConditionalOnProperty(prefix = WuxpCaptchaProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class WuxpCaptchaConfiguration {

    @Bean
    @ConditionalOnMissingBean(PictureCaptcha.class)
    public PictureCaptcha pictureCaptcha() {
        return new SimplePictureCaptcha();
    }

    @Bean
    @ConditionalOnMissingBean({SimpleMobileCaptcha.class})
    @ConditionalOnBean(MobileCaptchaSender.class)
    public MobileCaptcha mobileCaptcha() {
        return new SimpleMobileCaptcha();
    }

    @Bean
    @ConditionalOnMissingBean(QrCodeCaptcha.class)
    public QrCodeCaptcha qrCodeCaptcha() {

        return new SimpleQrCodeCaptcha();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(CombinationCaptcha.class)
    public CombinationCaptcha combinationCaptcha() {
        return new CombinationCaptcha(asList(
                this.pictureCaptcha(),
                this.mobileCaptcha(),
                this.qrCodeCaptcha()
        ));
    }

    @Bean
    @ConditionalOnMissingBean(CacheCaptchaStore.class)
    public CaptchaStore captchaStore() {
        return new CacheCaptchaStore();
    }

    @Bean
    @ConditionalOnMissingBean(DefaultPictureGenerator.class)
    public PictureCaptchaGenerator pictureCaptchaGenerator() {

        return new DefaultPictureGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(QrCodeCaptchaGenerator.class)
    public QrCodeCaptchaGenerator qrCodeCaptchaGenerator() {
        return new DefaultQrCodeCaptchaGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        Caffeine caffeine = Caffeine.newBuilder()
                .initialCapacity(500)
                .maximumSize(10 * 1000)
                .expireAfterWrite(
                        1200,
                        TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}
