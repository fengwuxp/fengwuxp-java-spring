package com.wuxp.security.authenticate.mobile;

import com.wuxp.security.authenticate.CaptchaWebAuthenticationDetailsSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@Slf4j
public class MobileCaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String mobileParameter = "mobile";

    private String mobileCaptchaParameter = "captcha";

    private CaptchaWebAuthenticationDetailsSource captchaWebAuthenticationDetailsSource;

    private boolean postOnly = true;

    MobileCaptchaAuthenticationFilter(String patternUrl) {
        super(new AntPathRequestMatcher(patternUrl, HttpMethod.POST.name()));
    }

    /**
     * 添加未认证用户认证信息，然后在provider里面进行正式认证
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException {
        if (postOnly && !httpServletRequest.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(MessageFormat.format("Authentication method not supported: {0}", httpServletRequest.getMethod()));
        }

        String mobilePhone = obtainMobile(httpServletRequest);
        String mobileCaptcha = obtainSmsCaptcha(httpServletRequest);
        if (mobilePhone == null) {
            mobilePhone = "";
        }
        if (mobileCaptcha == null) {
            mobileCaptcha = "";
        }
        mobilePhone = mobilePhone.trim();
        mobileCaptcha = mobileCaptcha.trim();

        MobileCaptchaAuthenticationToken authRequest = new MobileCaptchaAuthenticationToken(mobilePhone, mobileCaptcha);
        // Allow subclasses to set the "details" property
        setDetails(httpServletRequest, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    private String obtainSmsCaptcha(HttpServletRequest request) {
        return request.getParameter(mobileCaptchaParameter);
    }

    private void setDetails(HttpServletRequest request, MobileCaptchaAuthenticationToken authRequest) {
        authRequest.setDetails(captchaWebAuthenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

    public String getMobileCaptchaParameter() {
        return mobileCaptchaParameter;
    }

    public void setMobileCaptchaParameter(String mobileCaptchaParameter) {
        Assert.hasText(mobileCaptchaParameter, "MobileCaptcha parameter must not be empty or null");
        this.mobileCaptchaParameter = mobileCaptchaParameter;
    }

    public void setCaptchaWebAuthenticationDetailsSource(CaptchaWebAuthenticationDetailsSource captchaWebAuthenticationDetailsSource) {
        this.captchaWebAuthenticationDetailsSource = captchaWebAuthenticationDetailsSource;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
