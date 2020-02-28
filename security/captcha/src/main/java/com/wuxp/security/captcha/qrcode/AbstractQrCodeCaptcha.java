package com.wuxp.security.captcha.qrcode;

import com.wuxp.security.captcha.AbstractCaptchaBean;
import com.wuxp.security.captcha.CaptchaType;
import com.wuxp.security.captcha.CaptchaValue;
import com.wuxp.security.captcha.configuration.QrCodeCaptchaProperties;
import com.wuxp.security.captcha.constant.MessageKeyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


/**
 * 抽象的二维码验证码
 */
@Slf4j
public class AbstractQrCodeCaptcha extends AbstractCaptchaBean implements QrCodeCaptcha {


    @Override
    public QrCodeCaptchaGenerateResult generate(String useType, String key) {

        QrCodeCaptchaProperties qrCodeCaptchaProperties = wuxpCaptchaProperties.getQrCodeCaptchaProperties(useType);
        QrCodeCaptchaGenerateResult qrCodeCaptchaGenerateResult = QrCodeCaptchaGenerateResult.newInstance(useType, key, System.currentTimeMillis() + qrCodeCaptchaProperties.getExpired().toMillis());
        captchaStore.storeCaptcha(qrCodeCaptchaGenerateResult.getKey(), qrCodeCaptchaGenerateResult.getValue());
        return qrCodeCaptchaGenerateResult;
    }

    @Override
    public QrCodeState getQrCodeState(String key) {
        QrCodeCaptchaValue qrCodeCaptchaValue = captchaStore.readCaptcha(key, CaptchaType.QR_CODE.name());
        if (qrCodeCaptchaValue == null) {
            return null;
        }
        return qrCodeCaptchaValue.getQrCodeState();
    }

    @Override
    public void updateQrCodeState(String key, QrCodeState targetState) throws QrCodeUpdateStateCaptchaRuntimeException {
        String captchaType = CaptchaType.QR_CODE.name();
        QrCodeCaptchaValue qrCodeCaptchaValue = captchaStore.readCaptcha(key, captchaType);
        if (qrCodeCaptchaValue == null) {
            return;
        }
        if (targetState == null) {
            // 标记为NONE
//            targetSate = QrCodeState.NONE;
            return;
        }
//        if (QrCodeState.NONE.equals(targetSate)) {
//            // 移除
//            captchaStore.removeCaptcha(key, captchaType);
//            return;
//        }
        if (qrCodeCaptchaValue.getQrCodeState().equals(targetState)) {
            // 状态已经被更新
            throw new QrCodeUpdateStateCaptchaRuntimeException("不允许重复更新状态");
        }
        // TODO 同一个key串行支持
        // 更新缓存状态
        qrCodeCaptchaValue.setQrCodeState(targetState);
        captchaStore.storeCaptcha(key, qrCodeCaptchaValue);
    }

    @Override
    public boolean isEffective(String key) {
        QrCodeCaptchaValue qrCodeCaptchaValue = captchaStore.readCaptcha(key, CaptchaType.QR_CODE.name());
        if (qrCodeCaptchaValue == null) {
            return false;
        }
        return !qrCodeCaptchaValue.isExpired();
    }

    @Override
    public CaptchaVerifyResult verify(String key, CaptchaValue captchaValue) {
        String value = captchaValue.getValue();
        if (!StringUtils.hasText(value)) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.QR_CODE_CAPTCHA_EMPTY,
                            null,
                            "二维码不能为空",
                            null));
        }
        String captchaType = captchaValue.getCaptchaType();
        QrCodeCaptchaValue readCaptcha = captchaStore.readCaptcha(key, captchaType);
        if (readCaptcha == null) {
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.QR_CODE__CAPTCHA_NOT_EXIST,
                            null,
                            "该二维码不存在",
                            null));
        }

        if (readCaptcha.getExpireTime() <= System.currentTimeMillis()) {
            captchaStore.removeCaptcha(key, captchaType);
            return CaptchaVerifyResult.error(
                    messageSource.getMessage(MessageKeyConstant.QR_CODE__CAPTCHA_NOT_EXIST,
                            null,
                            "该二维码不存在",
                            null));
        }

        //不论成功或失败，立即失效
        captchaStore.removeCaptcha(key, captchaType);
        if (value.equalsIgnoreCase(readCaptcha.getValue())) {
            return CaptchaVerifyResult.success();
        }
        return CaptchaVerifyResult.error(
                messageSource.getMessage(MessageKeyConstant.QR_CODE__CAPTCHA_CHECK_ERROR,
                        null,
                        "二维码内容有误",
                        null));
    }
}
