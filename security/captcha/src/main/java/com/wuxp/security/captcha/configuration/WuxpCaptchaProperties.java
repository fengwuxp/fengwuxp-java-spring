package com.wuxp.security.captcha.configuration;

import com.wuxp.security.captcha.CaptchaUseType;
import com.wuxp.security.captcha.SimpleCaptchaUseType;
import com.wuxp.security.captcha.picture.PictureCaptchaType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * captcha properties
 *
 * @author wuxp
 */
@Data
@ConfigurationProperties(prefix = WuxpCaptchaProperties.PREFIX)
public class WuxpCaptchaProperties {

    /**
     * 配置 prefix
     */
    public static final String PREFIX = "wuxp.captcha";

    /**
     * 默认的图片验证码配置
     */
    private final static PictureCaptchaProperties DEFAULT_PICTURE = new PictureCaptchaProperties(4, 160, 50);

    /**
     * 默认的手机验证码配置
     */
    private final static MobileCaptchaProperties DEFAULT_MOBILE = new MobileCaptchaProperties();

    /**
     * 是否启用
     */
    private boolean enabled = false;


    /**
     * 默认的二维码相关配置
     */
    private QrCodeCaptchaProperties defaultQrCode = new QrCodeCaptchaProperties();

    /**
     * 图片验证码相关配置
     * key: {@link PictureCaptchaType}
     */
    private Map<String, PictureCaptchaProperties> picture;

    /**
     * 图片验证码相关配置
     * key: {@link SimpleCaptchaUseType}
     */
    private Map<String, MobileCaptchaProperties> mobile;

    /**
     * 二维码验证相关配置
     * key {@link SimpleCaptchaUseType}
     */
    private Map<String, QrCodeCaptchaProperties> qrCode;


    /**
     * @param type {@link PictureCaptchaType}
     * @return {@link PictureCaptchaProperties}
     */
    public PictureCaptchaProperties getPictureProperties(String type) {
        Object ignoreCaseProperties = this.getIgnoreCaseProperties(picture, type);
        if (ignoreCaseProperties == null) {
            return DEFAULT_PICTURE;
        }
        return (PictureCaptchaProperties) ignoreCaseProperties;
    }

    /**
     * @param type {@link SimpleCaptchaUseType}
     * @return {@link MobileCaptchaProperties}
     */
    public MobileCaptchaProperties getMobileCaptchaProperties(String type) {
        Object ignoreCaseProperties = this.getIgnoreCaseProperties(mobile, type);
        if (ignoreCaseProperties == null) {
            return DEFAULT_MOBILE;
        }
        return (MobileCaptchaProperties) ignoreCaseProperties;
    }

    /**
     * @param type {@link SimpleCaptchaUseType}
     * @return {@link QrCodeCaptchaProperties}
     */
    public QrCodeCaptchaProperties getQrCodeCaptchaProperties(String type) {
        Object ignoreCaseProperties = this.getIgnoreCaseProperties(qrCode, type);
        if (ignoreCaseProperties == null) {
            return defaultQrCode;
        }
        return (QrCodeCaptchaProperties) ignoreCaseProperties;
    }

    /**
     * 忽略大小写获取 properties
     *
     * @param map captcha 的配置
     * @param key {@link CaptchaUseType}
     * @return captcha Properties 配置
     */
    private Object getIgnoreCaseProperties(Map<String, ?> map, String key) {
        if (map == null) {
            return null;
        }
        Object o = map.get(key.toLowerCase());
        if (o == null) {
            o = map.get(key.toUpperCase());
        }
        return o;
    }
}
