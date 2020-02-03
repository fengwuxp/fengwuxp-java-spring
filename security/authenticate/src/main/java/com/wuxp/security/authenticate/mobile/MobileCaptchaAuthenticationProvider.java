package com.wuxp.security.authenticate.mobile;

import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.MessageFormat;

/**
 * 短信验证码鉴权
 */
@Slf4j
@Data
public class MobileCaptchaAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    private MobileCaptcha mobileCaptcha;

    public MobileCaptchaAuthenticationProvider() {
    }

    public MobileCaptchaAuthenticationProvider(UserDetailsService userDetailsService, MobileCaptcha mobileCaptcha) {
        this.userDetailsService = userDetailsService;
        this.mobileCaptcha = mobileCaptcha;
    }

    /**
     * 在这里认证用户信息
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileCaptchaAuthenticationToken authenticationToken = (MobileCaptchaAuthenticationToken) authentication;
        String mobilePhone = (String) authentication.getPrincipal();
        String mobilePoneCaptcha = (String) authenticationToken.getCredentials();
        //验证手机验证码
        MobileCaptchaValue mobileCaptchaValue = new MobileCaptchaValue(
                mobilePoneCaptcha,
                MessageFormat.format("{0}_{1}", MobileCaptchaType.LOGIN.name(),
                        mobilePhone),
                -1
        );
        Captcha.CaptchaVerifyResult verifyResult = mobileCaptcha.verify(mobilePhone, mobileCaptchaValue);

        if (verifyResult.isSuccess()) {
            throw new InternalAuthenticationServiceException(verifyResult.getErrorMessage());
        }

        UserDetails user = userDetailsService.loadUserByUsername(mobilePhone);
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        MobileCaptchaAuthenticationToken authenticationResult = new MobileCaptchaAuthenticationToken(user, null, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileCaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
