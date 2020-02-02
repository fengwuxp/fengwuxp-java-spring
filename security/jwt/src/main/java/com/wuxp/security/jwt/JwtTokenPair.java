package com.wuxp.security.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;



@Data
@Accessors(chain = true)
public class JwtTokenPair implements Serializable {

    private static final long serialVersionUID = -8518897818107784049L;

    // 访问token
    private String accessToken;

    // 刷新token
    private String refreshToken;
}
