package com.oak.member.constant;

import com.levin.commons.service.domain.Desc;

/**
 * @author laiy
 * create at 2020-02-13 22:49
 * @Description
 */
@Desc("用户Api上下文常量表达式")
public final class MemberApiContextInjectExprConstant {

    //登陆的用户key
    public static final String LOGIN_MEMBER_KEY = "memberInfo";

    //ip
    public static final String IP_ADDRESS_KEY = "ip";

    //requestInfo
    public static final String REQUEST_INFO_KEY = "requestInfo";

    /**
     * 注入请求的ip地址
     */
    public static final String INJECT_REQUEST_IP_EXPR = "#ip";

    /**
     * 注入用户登陆的token
     */
    public static final String INJECT_TOKEN_EXPR = "#memberInfo.token";

    /**
     * 注入登陆用用户的id
     */
    public static final String INJECT_MEMBER_ID_EXPR = "#memberInfo.id";

}
