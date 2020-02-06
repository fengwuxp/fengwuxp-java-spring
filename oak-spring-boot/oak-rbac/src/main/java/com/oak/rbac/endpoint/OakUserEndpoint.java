package com.oak.rbac.endpoint;


import com.oak.rbac.security.OakUser;
import com.oak.rbac.security.OakUserDetailsService;
import com.oak.rbac.security.UserSessionCacheHelper;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/oak_user")
public class OakUserEndpoint {

    @Autowired
    private UserSessionCacheHelper userSessionCacheHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 刷新token
     *
     * @return
     */
    @GetMapping("/refresh_token")
    public ApiResp<UserDetails> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        OakUser oakUser = userSessionCacheHelper.get(token);
        if (oakUser == null) {
            return RestfulApiRespFactory.error("token 已失效");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(oakUser.getUsername());
        OakUser principal = (OakUser) userDetails;

        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = jwtTokenProvider.generateAccessToken(principal.getUsername());
        principal.setToken(jwtTokenPayLoad.getToken());
        principal.setTokenExpired(jwtTokenPayLoad.getTokenExpireTimes());
        userSessionCacheHelper.join(jwtTokenPayLoad.getToken(), principal);

        return RestfulApiRespFactory.ok(userDetails);

    }

}
