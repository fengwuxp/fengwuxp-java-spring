package com.wuxp.security.authenticate;

import org.springframework.security.core.userdetails.UserDetails;

public interface PasswordUserDetails extends UserDetails {


    /**
     * 获取用户加密的密码验
     *
     * @return
     */
    String getCryptoSalt();
}
