//package com.wuxp.security.authority.url;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 用于鉴权请求url
// * 使用 Ant 路径匹配规则
// *
// * @see AntPathMatcher
// */
//@Slf4j
//public class RequestUrlAccessDecisionManager implements AccessDecisionManager {
//
//    private PathMatcher pathMatcher = new AntPathMatcher();
//
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        List<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
//                .filter(grantedAuthority -> grantedAuthority instanceof RequestUrlGrantedAuthority)
//                .collect(Collectors.toList());
//
//        for (ConfigAttribute configAttribute : configAttributes) {
//
//            boolean b = grantedAuthorities.stream()
//                    .anyMatch(grantedAuthority -> pathMatcher.match(grantedAuthority.getAuthority(), configAttribute.getAttribute()));
//            if (b) {
//                return;
//            }
//        }
//
//        throw new AccessDeniedException("您没有访问该url的权限");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute attribute) {
//        return attribute instanceof RequestUrlConfigAttribute;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return clazz.equals(RequestUrlGrantedAuthority.class);
//    }
//}
