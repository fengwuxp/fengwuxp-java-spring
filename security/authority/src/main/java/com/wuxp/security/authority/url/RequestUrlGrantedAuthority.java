package com.wuxp.security.authority.url;

import org.springframework.security.core.GrantedAuthority;

public class RequestUrlGrantedAuthority implements GrantedAuthority {


    /**
     * url expression
     * examples: /demo/list,/demo/**
     */
    private String urlExpression;

    public RequestUrlGrantedAuthority(String urlExpression) {
        this.urlExpression = urlExpression;
    }

    @Override
    public String getAuthority() {
        return this.urlExpression;
    }



}
