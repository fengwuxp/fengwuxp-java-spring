package com.wuxp.security.authenticate;

import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.CombinationCaptcha;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用来构造验证码验证信息
 * @author wxup
 */
@Slf4j
@Setter
public class CaptchaWebAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails>,
        BeanFactoryAware, InitializingBean {


    private BeanFactory beanFactory;

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

    private CombinationCaptcha combinationCaptcha;

    private PasswordLoginEnvironmentHolder passwordLoginEnvironmentHolder;


    @Override
    public CaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {

        LoginEnvironmentContext environmentContext = passwordLoginEnvironmentHolder.getContext(context);

        if (!environmentContext.isNeedPictureCaptcha()) {
            return new CaptchaAuthenticationDetails(context, Captcha.CaptchaVerifyResult.success());
        }

        String captchaKey = context.getParameter(captchaKeyName);
        String captchaValue = context.getParameter(captchaValueName);

        if (!StringUtils.hasText(captchaValue)) {
            throw new CaptchaException("验证码不能为空");
        }

        //verify captcha
        Captcha.CaptchaVerifyResult captchaVerifyResult = combinationCaptcha.verify(captchaKey, combinationCaptcha.genCaptchaValue(captchaKey, captchaValue));
        return new CaptchaAuthenticationDetails(context, captchaVerifyResult);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.combinationCaptcha == null) {
            this.combinationCaptcha = this.beanFactory.getBean(CombinationCaptcha.class);
        }
        if (this.passwordLoginEnvironmentHolder == null) {
            this.passwordLoginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }
    }
}
