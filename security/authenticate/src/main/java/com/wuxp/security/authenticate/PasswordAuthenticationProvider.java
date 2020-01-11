package com.wuxp.security.authenticate;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 使用密码登录方式鉴权提供者
 */
@Slf4j
public class PasswordAuthenticationProvider extends DaoAuthenticationProvider {

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

        // 调用父类方法完成密码验证
        super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);
    }

}
