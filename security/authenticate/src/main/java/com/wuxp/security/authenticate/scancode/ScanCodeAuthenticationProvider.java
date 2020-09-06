package com.wuxp.security.authenticate.scancode;

import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.SimpleCaptchaUseType;
import com.wuxp.security.captcha.qrcode.QrCodeCaptcha;
import com.wuxp.security.captcha.qrcode.QrCodeCaptchaValue;
import com.wuxp.security.captcha.qrcode.QrCodeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 扫码鉴权
 *
 * @author wuxp
 */
@Slf4j
public class ScanCodeAuthenticationProvider implements AuthenticationProvider {


    private AuthenticateSessionManager authenticateSessionManager;

    private QrCodeCaptcha qrCodeCaptcha;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ScanCodeAuthenticationToken authenticationToken = (ScanCodeAuthenticationToken) authentication;
        String accessToken = (String) authenticationToken.getCredentials();
        String credentials = (String) authenticationToken.getPrincipal();

        QrCodeState qrCodeState = qrCodeCaptcha.getQrCodeState(credentials);
        if (!QrCodeState.SCANNED.equals(qrCodeState)) {
            throw new InternalAuthenticationServiceException("请先扫描二维码");
        }

        // 验证扫码内容
        Captcha.CaptchaVerifyResult verifyResult = qrCodeCaptcha.verify(credentials, new QrCodeCaptchaValue(SimpleCaptchaUseType.LOGIN.name(), credentials, -1));
        if (!verifyResult.isSuccess()) {
            throw new InternalAuthenticationServiceException(verifyResult.getErrorMessage());
        }

        // 通过access_token 交换用户信息
        UserDetails userDetails = authenticateSessionManager.get(accessToken);
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        ScanCodeAuthenticationToken authenticationResult = new ScanCodeAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ScanCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public AuthenticateSessionManager getAuthenticateSessionManager() {
        return authenticateSessionManager;
    }

    public void setAuthenticateSessionManager(AuthenticateSessionManager authenticateSessionManager) {
        this.authenticateSessionManager = authenticateSessionManager;
    }

    public QrCodeCaptcha getQrCodeCaptcha() {
        return qrCodeCaptcha;
    }

    public void setQrCodeCaptcha(QrCodeCaptcha qrCodeCaptcha) {
        this.qrCodeCaptcha = qrCodeCaptcha;
    }
}
