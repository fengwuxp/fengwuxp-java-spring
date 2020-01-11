package com.wuxp.security.authenticate;

import lombok.Data;

/**
 * 账号登录失败的信息
 */
@Data
public class LoginEnvironmentContext {

    /**
     * 登录账号
     */
    private String username;

    /**
     * 登录来源ip
     */
    private String ip;

    /**
     * 登录设备标识
     */
    private String deviceId;

    /**
     * 登录方式
     */
    private String loginType;

    /**
     * 登录失败的次数
     */
    private int failureCount;

    /**
     * 首次登录时间
     */
    private long firstLoginTimes;

    /**
     * 最后登录时间
     */
    private long lastLoginTimes;

    /**
     * 是否需要图片验证码验证
     */
    private boolean needPictureCaptcha;
}
