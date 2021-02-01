package com.wuxp.security.authenticate.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Objects;

/**
 * 基于token交换的 {@link org.springframework.security.core.context.SecurityContext}
 * @author wuxp
 * @see JwtSecurityContextRepository
 */
public class TokenSecurityContextImpl extends SecurityContextImpl {

    /**
     * authenticate token
     */
    private String token;

    public TokenSecurityContextImpl() {
    }

    public TokenSecurityContextImpl(String token) {
        this.token = token;
    }

    public TokenSecurityContextImpl(Authentication authentication, String token) {
        super(authentication);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TokenSecurityContextImpl that = (TokenSecurityContextImpl) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }

    @Override
    public String toString() {
        return "TokenSecurityContextImpl{" +
                "token='" + token + '\'' +
                '}';
    }
}
