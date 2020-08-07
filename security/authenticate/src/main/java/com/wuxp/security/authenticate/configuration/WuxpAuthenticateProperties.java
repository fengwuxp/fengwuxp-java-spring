package com.wuxp.security.authenticate.configuration;

import com.wuxp.security.authenticate.form.FormLoginProperties;
import com.wuxp.security.authenticate.mobile.MobileCaptchaLoginProperties;
import com.wuxp.security.authenticate.scancode.ScanCodeLoginProperties;
import com.wuxp.security.authenticate.session.SessionLimitStrategy;
import com.wuxp.security.openid.OpenIdLoginProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置 spring security
 *
 * @author wxup
 */
@Data
@ConfigurationProperties(prefix = WuxpAuthenticateProperties.PREFIX)
public class WuxpAuthenticateProperties {

    public static final String PREFIX = "wuxp.security";

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 表单登录配置
     */
    private FormLoginProperties form = new FormLoginProperties();

    /**
     * 手机验证码登录
     */
    private MobileCaptchaLoginProperties mobileCaptcha = new MobileCaptchaLoginProperties();


    /**
     * openid 登录
     */
    private OpenIdLoginProperties openid = new OpenIdLoginProperties();

    /**
     * 扫码登录
     */
    private ScanCodeLoginProperties scanCode = new ScanCodeLoginProperties();

    /**
     * 在一段时间内登录连续失败次数的阈值
     */
    private int loginFailureThreshold = 5;

    /**
     * 连续登录失败的时间范围
     * 例如：5个小时内连续登录登录失败N次后，账号将会被限制登录
     */
    private Duration continuousLoginTimeRange = Duration.ofHours(5);

    /**
     * 连续登录失败{@code loginThreshold}次数后，账号在一段时间内被禁止登陆
     */
    private Duration limitLoginTimes = Duration.ofHours(2);

    /**
     * 同一账号同时登录最大用户数
     *
     * @see SessionLimitStrategy
     */
    private int maximumSessions = 1;

    /**
     * 不同客户端同一个账号最大可以登录的次数，
     * 默认 ：{@link #maximumSessions}
     */
    private Map<String, Integer> maximumClientSessions = new LinkedHashMap<>();


    public int getMaximumSessions() {
        return this.getMaximumSessions(null);
    }

    public int getMaximumSessions(String clientCode) {
        if (clientCode == null) {
            return maximumSessions;
        }
        return this.maximumClientSessions.getOrDefault(clientCode, maximumSessions);
    }
}
