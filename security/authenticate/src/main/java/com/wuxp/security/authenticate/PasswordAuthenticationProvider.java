package com.wuxp.security.authenticate;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * 使用密码登录方式鉴权提供者
 *
 * @author wxup
 */
@Slf4j
@Setter
public class PasswordAuthenticationProvider extends DaoAuthenticationProvider implements BeanFactoryAware {


    private BeanFactory beanFactory;

    public PasswordAuthenticationProvider() {
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        // 初始化登录上下文信息

        //获取验证码验证详情
        Object details = usernamePasswordAuthenticationToken.getDetails();
        CaptchaAuthenticationDetails captchaAuthenticationDetails = (CaptchaAuthenticationDetails) details;

        //验证失败
        if (!captchaAuthenticationDetails.isVerificationPassed()) {
            throw new CaptchaException(captchaAuthenticationDetails.getErrorMessage());
        }
        if (userDetails instanceof PasswordUserDetails) {
            PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(((PasswordUserDetails) userDetails).getCryptoSalt());
            Object presentedPassword = usernamePasswordAuthenticationToken.getCredentials();
            if (!passwordEncoder.matches(presentedPassword.toString(), userDetails.getPassword())) {
                if (log.isDebugEnabled()) {
                    logger.debug("Authentication failed: password does not match stored value");
                }
                throw new BadCredentialsException(messages.getMessage(
                        "PasswordAuthenticationProvider.badCredentials",
                        "Bad credentials"));
            }
            return;
        }

        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);
    }

    @Override
    protected void doAfterPropertiesSet() {
        if (this.getUserDetailsService() == null) {
            this.setUserDetailsService(this.beanFactory.getBean(UserDetailsService.class));
        }
        super.doAfterPropertiesSet();
    }
}
