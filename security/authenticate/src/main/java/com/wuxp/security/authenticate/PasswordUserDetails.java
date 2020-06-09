package com.wuxp.security.authenticate;

import org.springframework.security.core.userdetails.UserDetails;

import java.beans.Transient;

/**
 * 带密码的用户
 *
 * @author wxup
 */
public interface PasswordUserDetails extends UserDetails {


    /**
     * 获取用户加密的密码验
     * 该方法必须加上 {@link Transient}，防止密码加密盐泄漏
     *
     * @return
     */
    @Transient
    String getCryptoSalt();
}
