package com.wuxp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * jwt token provider
 */
@Slf4j
@Data
public class JwtTokenProvider implements BeanFactoryAware, InitializingBean {


    private BeanFactory beanFactory;

    private SecretKey key;

    private JwtProperties jwtProperties;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }

    }

    public JwtTokenPair jwtTokenPair(String userUniqueIdentifier) {

        JwtTokenPair jwtTokenPair = new JwtTokenPair();
        return jwtTokenPair.setAccessToken(this.generateAccessToken(userUniqueIdentifier))
                .setRefreshToken(this.generateRefreshToken(userUniqueIdentifier));
    }

    /**
     * generate access token
     */
    public String generateAccessToken(String userUniqueIdentifier) {
        return spliceToken(jwtProperties.getHeaderPrefix(),
                generateToken(
                        jwtProperties.getSubject(),
                        jwtProperties.getIssuer(),
                        userUniqueIdentifier,
                        jwtProperties.getExpireTimeout()));
    }

    /**
     * generate refresh token
     */
    public String generateRefreshToken(String userUniqueIdentifier) {
        return spliceToken(jwtProperties.getHeaderPrefix(),
                generateToken(
                        jwtProperties.getSubject(),
                        jwtProperties.getIssuer(),
                        userUniqueIdentifier,
                        jwtProperties.getRefreshExpireTimeout()));
    }


    public Jws<Claims> parse(String token) throws UnsupportedJwtException {
        String prefix = jwtProperties.getHeaderPrefix();
        if (StringUtils.isEmpty(token)) {
            log.warn("Token is empty!");
            throw new UnsupportedJwtException("Token is empty!");
        }
        if (!token.startsWith(prefix)) {
            log.warn("Don't have prefix {}!", prefix);
            throw new UnsupportedJwtException("Unsupported jwt token!");
        }
        token = token.substring(prefix.length());
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
    }

    public boolean check(String jwtToken) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = this.parse(jwtToken);
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        }

        return true;
    }

    /**
     * generate token
     */
    protected String generateToken(String subject, String issuer, String audience, Duration timeout) {
        return Jwts.builder()
                .signWith(getKey())
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setAudience(audience)
                .setExpiration(Date.from(Instant.now().plusSeconds(timeout.getSeconds())))
                .compact();
    }


    private String spliceToken(String prefix, String token) {

        return prefix + token;
    }

    private SecretKey getKey() {
        if (key != null) {
            return key;
        }
        String secret = jwtProperties.getSecret();
        if (StringUtils.isEmpty(secret)) {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            return key;
        }
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return key;
    }
}
