package com.wuxp.security.authenticate.form;

import com.alibaba.fastjson.JSON;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import com.wuxp.security.authenticate.LoginEnvironmentContext;
import com.wuxp.security.authenticate.configuration.WuxpSecurityProperties;
import com.wuxp.security.captcha.configuration.WuxpCaptchaProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler, BeanFactoryAware, InitializingBean {


    private BeanFactory beanFactory;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private LockedUserDetailsService lockedUserDetailsService;

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

        log.error("认证失败", exception);

        LoginEnvironmentContext loginEnvironmentContext = loginEnvironmentHolder.getContextAndIncreaseFailureCount(request);

        int failureCount = loginEnvironmentContext.getFailureCount();
        if (failureCount >= loginFailureThreshold) {
            // 锁定该账户
            this.lockedUserDetailsService.lockUserByUsername(loginEnvironmentContext.getUsername(), this.limitLoginTimes);
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, Object> map = new HashMap<>();
        String message = exception.getMessage();
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        }
        map.put("message", StringUtils.hasText(message) ? message : "发生非预期的异常");
        //是否需要图片验证码验证
        map.put("needPictureCaptcha", loginEnvironmentContext.isNeedPictureCaptcha());
        //返回Json数据
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(JSON.toJSONString(map));

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.lockedUserDetailsService == null) {
            this.lockedUserDetailsService = this.beanFactory.getBean(LockedUserDetailsService.class);
        }

        if (this.loginEnvironmentHolder == null) {
            this.loginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }

        this.loginFailureThreshold = beanFactory.getBean(WuxpSecurityProperties.class).getLoginFailureThreshold();
        this.continuousLoginTimeRange = beanFactory.getBean(WuxpSecurityProperties.class).getContinuousLoginTimeRange();
        this.limitLoginTimes = beanFactory.getBean(WuxpSecurityProperties.class).getLimitLoginTimes();
    }
}
