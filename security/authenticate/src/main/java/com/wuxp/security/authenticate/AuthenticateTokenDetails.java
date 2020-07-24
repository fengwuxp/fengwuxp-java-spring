package com.wuxp.security.authenticate;

import java.util.Date;

/**
 * 鉴权token 信息
 *
 * @author wuxp
 */
public interface AuthenticateTokenDetails {


    /**
     * 用户token
     *
     * @return
     */
    String getToken();

    /**
     * 设置登录token
     *
     * @param token
     */
    void setToken(String token);


    /**
     * token 过期时间
     *
     * @return
     */
    Date getTokenExpired();

    /**
     * 设置token的过期时间
     *
     * @param tokenExpired token 过期时间
     */
    void setTokenExpired(Date tokenExpired);


    /**
     * token有效的毫秒数，客户端使用
     *
     * @return
     */
    long getEffectiveMilliseconds();

    /**
     * 设置 token有效的毫秒数
     *
     * @param effectiveMilliseconds token有效的毫秒数
     */
    void setEffectiveMilliseconds(long effectiveMilliseconds);


    /**
     * refreshToken
     *
     * @return refreshToken
     */
    String getRefreshToken();

    /**
     * 设置登录token
     *
     * @param refreshToken 刷新token
     */
    void setRefreshToken(String refreshToken);


    /**
     * token 过期时间
     *
     * @return
     */
    Date getRefreshTokenExpired();

    /**
     * 设置token的过期时间
     *
     * @param refreshTokenExpired refresh token 过期时间
     */
    void setRefreshTokenExpired(Date refreshTokenExpired);

    /**
     * refresh token有效的毫秒数，客户端使用
     *
     * @return
     */
    long getRefreshEffectiveMilliseconds();

    /**
     * 设置 refresh token有效的毫秒数
     *
     * @param refreshEffectiveMilliseconds refresh token有效的毫秒数
     */
    void setRefreshEffectiveMilliseconds(long refreshEffectiveMilliseconds);
}
