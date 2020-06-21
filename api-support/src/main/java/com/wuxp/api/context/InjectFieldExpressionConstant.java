package com.wuxp.api.context;


/**
 * 常见的注入表达式
 *
 * @author wxup
 */
public final class InjectFieldExpressionConstant {

//    /**
//     * rabc 用户在上下文的名称
//     */
//    public static final String RBAC_ADMIN_VARIABLE_NAME = "admin";
//
//    /**
//     * 注入RBAC admin 账号的id
//     */
//    public static final String INJECT_RBAC_ADMIN_USER_ID = "#admin.id";
//
//    /**
//     * 注入RBAC admin 账号的name
//     */
//    public static final String INJECT_RBAC_ADMIN_USER_NAME = "#admin.name";


    /**
     * {@link javax.servlet.http.HttpServletRequest} 在spel上下文中的名称
     */
    public static final String HTTP_SERVLET_REQUEST_VARIABLE_NAME = "request";

    /**
     * 鉴权请求头在 在spel上下文中的名称
     */
    public static final String AUTHORIZATION_HEADER_VARIABLE_NAME = "authorizationHeaderName";


    /**
     * 登录用户持有者在 spel上下文中的名称
     */
    public static final String USER_HOLDER_VARIABLE_NAME = "userHolder";

    /**
     * 注入ip地址的表达式
     * {@link com.wuxp.basic.utils.IpAddressUtils#try2GetUserRealIPAddr(javax.servlet.http.HttpServletRequest)}
     */
    public static final String INJECT_IP = "T(com.wuxp.basic.utils.IpAddressUtils).try2GetUserRealIPAddr(#request)";

    /**
     * 用户注入鉴权请求头的信息
     */
    public static final String INJECT_AUTHORIZATION = "#request.getHeader(#authorizationHeaderName)";

    /**
     * 用于注入用户id，userHolder 必须要有一个 getUserId的方法
     */
    public static final String INJECT_USER_ID = "#userHolder.getUserId(#request)";
}
