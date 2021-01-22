package com.wuxp.security.authenticate.context;

import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

/**
 * @author wuxp
 */
public class JwtSecurityContextRepository implements SecurityContextRepository {

    private final String springSecurityContextKey = SPRING_SECURITY_CONTEXT_KEY;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtProperties jwtProperties;

    private final AuthenticateSessionManager authenticateSessionManager;

    public JwtSecurityContextRepository(JwtTokenProvider jwtTokenProvider, JwtProperties jwtProperties, AuthenticateSessionManager authenticateSessionManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtProperties = jwtProperties;
        this.authenticateSessionManager = authenticateSessionManager;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName());
        try {
            jwtTokenProvider.parse(authorizationHeader);
            return new TokenSecurityContextImpl(authorizationHeader);
        } catch (Exception exception) {
            request.setAttribute(ERROR_EXCEPTION_ATTRIBUTE, exception);
        }
        return new TokenSecurityContextImpl();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(springSecurityContextKey, context);
        TokenSecurityContextImpl securityContext = (TokenSecurityContextImpl) context;
        String token = securityContext.getToken();
        if (token == null) {
            return;
        }
        UserDetails userDetails = this.authenticateSessionManager.get(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return request.getAttribute(springSecurityContextKey) != null;
    }


}
