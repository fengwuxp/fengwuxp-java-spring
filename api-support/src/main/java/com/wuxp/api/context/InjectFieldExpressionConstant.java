package com.wuxp.api.context;


import java.text.MessageFormat;

/**
 * 常见的注入表达式
 */
public final class InjectFieldExpressionConstant {

    /**
     * rabc 用户在上下文的名称
     */
    public static final String RBAC_ADMIN_VARIABLE_NAME = "admin";

    /**
     * 注入RBAC admin 账号的id
     */
    public static final String INJECT_RBAC_ADMIN_USER_ID = "#admin.id";

    /**
     * 注入RBAC admin 账号的name
     */
    public static final String INJECT_RBAC_ADMIN_USER_NAME = "#admin.name";

}
