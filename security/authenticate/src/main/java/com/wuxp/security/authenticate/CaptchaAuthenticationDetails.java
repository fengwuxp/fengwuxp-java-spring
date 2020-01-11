package com.wuxp.security.authenticate;

import com.wuxp.security.captcha.Captcha;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 保存额外的认证信息，例如图片验证码、手机验证码等
 */
public class CaptchaAuthenticationDetails extends WebAuthenticationDetails {


    /**
     * 验证是否通过
     */
    private Captcha.CaptchaVerifyResult captchaVerifyResult;


    public CaptchaAuthenticationDetails(HttpServletRequest request, Captcha.CaptchaVerifyResult captchaVerifyResult) {
        super(request);
        this.captchaVerifyResult = captchaVerifyResult;

    }

    public boolean isVerificationPassed() {
        return this.captchaVerifyResult.isSuccess();
    }

    public String getErrorMessage() {
        return this.captchaVerifyResult.getErrorMessage();
    }
}
