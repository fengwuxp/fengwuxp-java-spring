package com.wuxp.security.authenticate.form;

import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import com.wuxp.security.authenticate.LoginEnvironmentContext;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于表单鉴权的处理器
 *
 * @author wxup
 */
@Slf4j
@Data
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler, BeanFactoryAware, InitializingBean, HttpMessageResponseWriter {


    private BeanFactory beanFactory;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private LockedUserDetailsService lockedUserDetailsService;

    /**
     * 连续登录失败的阈值
     */
    private int loginFailureThreshold;


    /**
     * 连续登录失败的时间范围
     * 例如：2个小时内连续登录登录失败N次后，账号将会被限制登录
     */
    private Duration continuousLoginTimeRange;

    /**
     * 连续登录失败{@code loginThreshold}次数后，账号在一段时间内被禁止登陆
     */
    private Duration limitLoginTimes;

    public FormAuthenticationFailureHandler() {
    }

    public FormAuthenticationFailureHandler(PasswordLoginEnvironmentHolder loginEnvironmentHolder, LockedUserDetailsService lockedUserDetailsService) {
        this.loginEnvironmentHolder = loginEnvironmentHolder;
        this.lockedUserDetailsService = lockedUserDetailsService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("认证失败", exception);
        }

        LoginEnvironmentContext loginEnvironmentContext = loginEnvironmentHolder.getContextAndIncreaseFailureCount(request);

        int failureCount = loginEnvironmentContext.getFailureCount();
        if (failureCount >= loginFailureThreshold) {
            // 锁定该账户
            this.lockedUserDetailsService.lockUserByUsername(loginEnvironmentContext.getUsername(), this.limitLoginTimes);
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        String message = exception.getMessage();
        if (exception instanceof UsernameNotFoundException || exception instanceof InternalAuthenticationServiceException) {
            Throwable cause = exception.getCause();
            if (cause instanceof LockedException) {
                // 用户被锁定
                message = exception.getMessage();
            } else {
                message = "用户不存在";
            }

        } else if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        } else {
            message = message == null ? "发生非预期的异常" : message;
        }
        Map<String, Object> map = new HashMap<>();
        //是否需要图片验证码验证
        map.put("needPictureCaptcha", loginEnvironmentContext.isNeedPictureCaptcha());
        //返回Json数据
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        this.writeJson(response, RestfulApiRespFactory.error(message, map));

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.lockedUserDetailsService == null) {
            this.lockedUserDetailsService = this.beanFactory.getBean(LockedUserDetailsService.class);
        }

        if (this.loginEnvironmentHolder == null) {
            this.loginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }

        this.loginFailureThreshold = beanFactory.getBean(WuxpAuthenticateProperties.class).getLoginFailureThreshold();
        this.continuousLoginTimeRange = beanFactory.getBean(WuxpAuthenticateProperties.class).getContinuousLoginTimeRange();
        this.limitLoginTimes = beanFactory.getBean(WuxpAuthenticateProperties.class).getLimitLoginTimes();
    }
}
