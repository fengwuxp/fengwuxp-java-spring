package com.wuxp.security.authenticate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 通过请求头加载用户信息的 UserDetailService
 */
public interface RequestHeaderAuthorizationDetailsService /*extends UserDetailsService*/ {

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param authorizationToken  http request header authorization info
     *
     * @return a fully populated user record (never <code>null</code>)
     *
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    UserDetails loadUserByAuthorizationToken(String authorizationToken) throws UsernameNotFoundException;

}
