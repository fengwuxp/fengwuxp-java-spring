package com.wuxp.security.captcha.mobile;

import com.wuxp.security.captcha.AbstractCaptchaBean;
import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import com.wuxp.security.captcha.configuration.MobileCaptchaProperties;
import com.wuxp.security.captcha.constant.MessageKeyConstant;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;

@Slf4j
@Setter
public abstract class AbstractMobileCaptcha extends AbstractCaptchaBean implements MobileCaptcha {


    private MobileCaptchaSender mobileCaptchaSender;


    /**
     * generate captcha
     *
     * @param useType     captcha scenes to be used
     * @param mobilePhone mobile hone
     * @return
     */
    @Override
    public MobileCaptchaGenerateResult generate(String useType, String mobilePhone) {

        MobileCaptchaProperties mobileCaptchaProperties = wuxpCaptchaProperties.getMobileCaptchaProperties(useType);
        MobileCaptchaGenerateResult captchaGenerateResult = MobileCaptchaGenerateResult.newInstance(
                useType,
                mobilePhone,
                System.currentTimeMillis() + mobileCaptchaProperties.getExpired().toMillis());
        MobileCaptchaValue mobileCaptchaValue = captchaGenerateResult.getValue();
        String resultKey = captchaGenerateResult.getKey();
        MobileCaptchaSender.MobileCaptchaSenderResult result = mobileCaptchaSender.send(useType, resultKey, mobileCaptchaValue.getValue());
        captchaStore.storeCaptcha(resultKey, mobileCaptchaValue);
        captchaGenerateResult.setErrorMessage(result.getMessage());
        return captchaGenerateResult;
    }

    @Override
    public boolean isEffective(String key) {
        MobileCaptchaValue mobileCaptchaValue = captchaStore.readCaptcha(key, CaptchaType.MOBILE.name());
        if (mobileCaptchaValue == null) {
            return false;
        }
        return !mobileCaptchaValue.isExpired();
    }

    @Override
    public CaptchaVerifyResult verify(String key, CaptchaValue captchaValue) {
        if (captchaValue == null || !StringUtils.hasText(captchaValue.getValue())) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.MOBILE_CAPTCHA_EMPTY,
                            null,
                            "验证码不能为空",
                            null));
        }
        String captchaType = captchaValue.getCaptchaType();
        MobileCaptchaValue mobileCaptchaValue = captchaStore.readCaptcha(key, captchaType);
        if (mobileCaptchaValue == null) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.MOBILE_CAPTCHA_NOT_EXIST,
                            null,
                            "验证码不存在或已失效",
                            null));
        }

        if (mobileCaptchaValue.getExpireTime() <= System.currentTimeMillis()) {
            captchaStore.removeCaptcha(key, captchaType);
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.PICTURE_CAPTCHA_NOT_EXIST,
                            null,
                            "该图片验证码不存在",
                            null));
        }

        MobileCaptchaProperties mobileCaptchaProperties = wuxpCaptchaProperties.getMobileCaptchaProperties(captchaValue.getUseType());
        if (!mobileCaptchaValue.getValue().equals(captchaValue.getValue())) {
            mobileCaptchaValue.addFailureCount();
            int failureVerifyCount = mobileCaptchaValue.getFailureVerifyCount();
            if (failureVerifyCount < mobileCaptchaProperties.getMaxReties()) {
                captchaStore.storeCaptcha(key, mobileCaptchaValue);
            } else {
                //remove
                captchaStore.removeCaptcha(key, captchaType);
            }
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.MOBILE_CAPTCHA_CHECK_ERROR,
                            null,
                            "验证码输入错误",
                            null));
        }
        captchaStore.removeCaptcha(key, captchaType);
        return CaptchaVerifyResult.success();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        BeanFactory beanFactory = this.beanFactory;
        if (this.mobileCaptchaSender == null) {
            this.mobileCaptchaSender = beanFactory.getBean(MobileCaptchaSender.class);
        }
    }
}
