package com.wuxp.security.example.security;

import com.wuxp.security.authenticate.PasswordUserDetails;
import com.wuxp.security.authenticate.session.AbstractAuthenticateSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuxp
 */
@Component
public class MockAuthenticateSessionManager extends AbstractAuthenticateSessionManager {


    public MockAuthenticateSessionManager( ) {
        super(User.class);
    }

    @Override
    protected PasswordUserDetails findUserByToken(String token) throws UsernameNotFoundException {
        return null;
    }

    @Override
    protected void tryRemoveDbToken(PasswordUserDetails user) {

    }

    @Override
    protected List<String> getTokensByDb(String userName, String clientCode) {
        return null;
    }

    @Override
    protected String getUserCacheName() {
        return null;
    }

    @Override
    protected String getTokenCacheName() {
        return null;
    }

    @Override
    protected String getKickOutReasonCacheName() {
        return null;
    }

    @Override
    public UserDetails join(UserDetails userDetails) {
        return null;
    }

    @Override
    public void tryKickOut(UserDetails userDetails, String reason) {

    }
}
