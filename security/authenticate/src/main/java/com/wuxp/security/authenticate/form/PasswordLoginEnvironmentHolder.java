package com.wuxp.security.authenticate.form;

import com.wuxp.basic.utils.IpAddressUtils;
import com.wuxp.security.authenticate.AuthenticateType;
import com.wuxp.security.authenticate.LoginEnvironmentContext;
import com.wuxp.security.authenticate.LoginEnvironmentHolder;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;

@Slf4j
@Setter
public class PasswordLoginEnvironmentHolder implements LoginEnvironmentHolder, BeanFactoryAware, InitializingBean {

    @Autowired
    private CacheManager cacheManager;

    private BeanFactory beanFactory;

    private long continuousLoginTimes;

    private int showCaptchaByFailureCount;


    private static final String BROWSER_LOGIN_CONTEXT = "PASSWORD_LOGIN_CONTEXT";

    @Override
    public LoginEnvironmentContext getContext(HttpServletRequest request) {
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        if (!StringUtils.hasText(username)) {
//            throw new AuthenticationException("用户名不能为空")
            username = "";
        }
        Cache cache = cacheManager.getCache(BROWSER_LOGIN_CONTEXT);
        assert cache != null;
        LoginEnvironmentContext loginEnvironmentContext = cache.get(username, LoginEnvironmentContext.class);
        if (loginEnvironmentContext == null) {
            loginEnvironmentContext = getLoginEnvironmentContext(request, username);
            cache.put(username, loginEnvironmentContext);
        } else {
            // 最后登录时间超过 continuousLoginTimeRange，重置登录的错误次数
            long lastLoginTimes = loginEnvironmentContext.getLastLoginTimes();
            long l = System.currentTimeMillis() - lastLoginTimes;
            if (l > this.continuousLoginTimes) {
                // 移除旧的 登录山下文
                this.remove(request);
                loginEnvironmentContext = this.getLoginEnvironmentContext(request, username);
            }
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
        assert cache != null;
        cache.put(id, context);
        return context;
    }

    @Override
    public void remove(HttpServletRequest request) {
        Cache cache = cacheManager.getCache(BROWSER_LOGIN_CONTEXT);
        assert cache != null;
        String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        cache.evictIfPresent(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        WuxpAuthenticateProperties properties = this.beanFactory.getBean(WuxpAuthenticateProperties.class);
        this.continuousLoginTimes = properties.getContinuousLoginTimeRange().toMillis();
        this.showCaptchaByFailureCount = properties.getForm().getShowCaptchaByFailureCount();
    }


    private LoginEnvironmentContext getLoginEnvironmentContext(HttpServletRequest request, String username) {
        LoginEnvironmentContext loginEnvironmentContext;
        loginEnvironmentContext = new LoginEnvironmentContext();
        loginEnvironmentContext.setUsername(username);
        loginEnvironmentContext.setIp(IpAddressUtils.try2GetUserRealIPAddr(request));
        long firstLoginTimes = System.currentTimeMillis();
        loginEnvironmentContext.setFirstLoginTimes(firstLoginTimes);
        loginEnvironmentContext.setLastLoginTimes(firstLoginTimes);
        loginEnvironmentContext.setLoginType(AuthenticateType.PASSWORD.name());
        loginEnvironmentContext.setFailureCount(0);
        this.setNeedPictureCaptcha(loginEnvironmentContext);
        return loginEnvironmentContext;
    }

    private void setNeedPictureCaptcha(LoginEnvironmentContext loginEnvironmentContext) {
        int showCaptchaByFailureCount = this.showCaptchaByFailureCount;
        loginEnvironmentContext.setNeedPictureCaptcha(loginEnvironmentContext.getFailureCount() >= showCaptchaByFailureCount);
    }
}
