package com.wuxp.security.authenticate;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CombinationCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * 用来构造验证码验证信息
 */
@Slf4j
public class CaptchaWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails> {


    /**
     * 验证码标识
     */
    @Value("${captcha.key.name:captchaKey}")
    private String captchaKeyName;

    /**
     * 验证码内容
     */
    @Value("${captcha.value.param:captchaValue}")
    private String captchaValueName;

    @Autowired
    private CombinationCaptcha combinationCaptcha;


    @Override
    public CaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
        String captchaKey = context.getParameter(captchaKeyName);
        String captchaValue = context.getParameter(captchaValueName);
        //verify captcha
        Captcha.CaptchaVerifyResult captchaVerifyResult = combinationCaptcha.verify(captchaKey, combinationCaptcha.genCaptchaValue(captchaKey, captchaValue));
        return new CaptchaAuthenticationDetails(context, captchaVerifyResult);
    }

}
