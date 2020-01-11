package com.wuxp.security.authority.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;

/**
 * 用于鉴权请求url
 * 使用 Ant 路径匹配规则
 *
 * @see AntPathMatcher
 */
@Slf4j
public class RequestUrlAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof RequestUrlConfigAttribute;
    }

    @Override
    public boolean supports(Class clazz) {
        return clazz.equals(FilterInvocation.class);
    }


    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            // 未登录拒绝授权
            return ACCESS_DENIED;
        }

        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (pathMatcher.match(authority.getAuthority(), attribute.getAttribute())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }
}
