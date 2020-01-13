package com.wuxp.security.authenticate.form;

import com.wuxp.basic.utils.IpAddressUtils;
import com.wuxp.security.authenticate.AuthenticateType;
import com.wuxp.security.authenticate.LoginEnvironmentContext;
import com.wuxp.security.authenticate.LoginEnvironmentHolder;
import com.wuxp.security.authenticate.configuration.WuxpSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

@Slf4j
public class PasswordLoginEnvironmentHolder implements LoginEnvironmentHolder {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private WuxpSecurityProperties wuxpSecurityProperties;

    private static final String BROWSER_LOGIN_CONTEXT = "PASSWORD_LOGIN_CONTEXT";

    @Override
    public LoginEnvironmentContext getContext(HttpServletRequest request) {
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        Cache cache = cacheManager.getCache(BROWSER_LOGIN_CONTEXT);
        assert cache != null;
        LoginEnvironmentContext loginEnvironmentContext = cache.get(username, LoginEnvironmentContext.class);
        if (loginEnvironmentContext == null) {
            loginEnvironmentContext = new LoginEnvironmentContext();
            loginEnvironmentContext.setUsername(username);
            loginEnvironmentContext.setIp(IpAddressUtils.try2GetUserRealIPAddr(request));
            long firstLoginTimes = System.currentTimeMillis();
            loginEnvironmentContext.setFirstLoginTimes(firstLoginTimes);
            loginEnvironmentContext.setLastLoginTimes(firstLoginTimes);
            loginEnvironmentContext.setLoginType(AuthenticateType.PASSWORD.name());
            loginEnvironmentContext.setFailureCount(0);
            this.setNeedPictureCaptcha(loginEnvironmentContext);
            cache.put(username, loginEnvironmentContext);
        }
        return loginEnvironmentContext;
    }

    @Override
    public LoginEnvironmentContext getContextAndIncreaseFailureCount(HttpServletRequest request) {
        LoginEnvironmentContext context = this.getContext(request);
        int failureCount = context.getFailureCount();
        context.setFailureCount(++failureCount);
        this.setNeedPictureCaptcha(context);
        String id = request.getSession().getId();
        Cache cache = cacheManager.getCache(BROWSER_LOGIN_CONTEXT);
        cache.put(id, context);
        return context;
    }

    private void setNeedPictureCaptcha(LoginEnvironmentContext loginEnvironmentContext) {
        int showCaptchaByFailureCount = wuxpSecurityProperties.getForm().getShowCaptchaByFailureCount();
        loginEnvironmentContext.setNeedPictureCaptcha(loginEnvironmentContext.getFailureCount() >= showCaptchaByFailureCount);
    }
}
