package com.wuxp.api.log;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 操作日志描述对象
 * @author wxup
 */
@Data
@Accessors(chain = true)
public class ApiLogModel implements java.io.Serializable {

    private static final long serialVersionUID = 7015769186384892841L;

    public static final String USER_AGENT_HEADER = "User-Agent";

    /**
     * 请求Uri
     */
    private String uri;

    /**
     * 登录来源ip
     */
    private String ip;

    /**
     * 请求的client 标记 (userAgent)
     */
    private String clientUserAgent;

    /**
     * 方法描述
     */
    private String methodDesc;

    /**
     * 操作类型
     */
    private String type = "默认";

    /**
     * 动作
     */
    private String action;


    /**
     * 日志描述
     */
    private String description;

    /**
     * 经过spel表达式计算后的内容
     */
    private String content;


    /**
     * 操作人
     */
    private Object authentication;

    /**
     * 操作的目标资源
     */
    private String targetResourceId;


}
