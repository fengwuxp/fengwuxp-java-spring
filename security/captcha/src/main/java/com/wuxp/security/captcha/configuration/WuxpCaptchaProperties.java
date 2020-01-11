package com.wuxp.security.captcha.configuration;

import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import com.wuxp.security.captcha.picture.PictureCaptchaType;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * captcha properties
 */
@Data
@ConfigurationProperties(prefix = WuxpCaptchaProperties.PREFIX)
public class WuxpCaptchaProperties {

    public static final String PREFIX = "wuxp.captcha";

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 默认的图片验证码配置
     */
    private PictureCaptchaProperties defaultPicture = new PictureCaptchaProperties(4, 160, 50);

    /**
     * 默认的手机验证码配置
     */
    private MobileCaptchaProperties defaultMobile = new MobileCaptchaProperties();

    /**
     * 默认的二维码相关配置
     */
    private QrCodeCaptchaProperties defaultQrCode = new QrCodeCaptchaProperties();

    /**
     * 图片验证码相关配置
     */
    private Map<String/*type*/, PictureCaptchaProperties> picture;

    /**
     * 图片验证码相关配置
     */
    private Map<String/*type*/, MobileCaptchaProperties> mobile;

    /**
     * 二维码验证相关配置
     */
    private Map<String/*type*/, QrCodeCaptchaProperties> qrCode;


    /**
     * @param type {@link PictureCaptchaType}
     * @return
     */
    public PictureCaptchaProperties getPictureProperties(String type) {
        Object ignoreCaseProperties = this.getIgnoreCaseProperties(picture, type);
        if (ignoreCaseProperties == null) {
            return defaultPicture;
        }
        return (PictureCaptchaProperties) ignoreCaseProperties;
    }

    /**
     * @param type {@link MobileCaptchaType}
     * @return
     */
    public MobileCaptchaProperties getMobileCaptchaProperties(String type) {
        Object ignoreCaseProperties = this.getIgnoreCaseProperties(mobile, type);
        if (ignoreCaseProperties == null) {
            return defaultMobile;
        }
        return (MobileCaptchaProperties) ignoreCaseProperties;
    }

    /**
     * @param type {@link QrCodeCaptchaType}
     * @return
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
     * @param map
     * @param key
     * @return
     */
    private Object getIgnoreCaseProperties(Map map, String key) {
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
