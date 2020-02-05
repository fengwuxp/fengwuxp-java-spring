package com.wuxp.security.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class JwtTokenPair implements Serializable {

    private static final long serialVersionUID = -8518897818107784049L;

    // 访问token
    private JwtTokenPayLoad accessToken;


    // 刷新token
    private JwtTokenPayLoad refreshToken;


    @Accessors(chain = true)
    @Data
    public static class JwtTokenPayLoad implements Serializable {

        private static final long serialVersionUID = 4829277168255853084L;

        // 访问token
        private String token;

        // token的过期时间
        private Date tokenExpireTimes;
    }
}
