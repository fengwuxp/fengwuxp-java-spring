package com.wuxp.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

import java.time.Duration;


/**
 * @author wuxp
 */
@Data
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {

    public static final String PREFIX = "spring.jwt";

    public static final String AUTHENTICATION_PREFIX = "Bearer ";


    /**
     * 是否可用
     */
    private boolean enabled = true;


    /**
     * jwt subject
     */
    private String subject = "api-server";

    /**
     * jwt issuer
     */
    private String issuer = "api-server";

    /**
     * Secret
     * 项目中要覆盖这个配置
     */
    private String secret = "oskdfn8382KJjk42iwlilm9239423832lksd823nJKKi3jnldkjs2323jklks3i3i";

    /**
     * Generate token to set expire time
     * user?
     */
    private Duration expireTimeout = Duration.ofSeconds(7200);


    /**
     * refresh jwt token 有效天数
     */
    private Duration refreshExpireTimeout = Duration.ofDays(7);


    /**
     * token 请求头名称
     */
    private String headerName = HttpHeaders.AUTHORIZATION;

    /**
     * 请求token的前缀
     */
    private String headerPrefix = AUTHENTICATION_PREFIX;
}
