package com.wuxp.api.context;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 生成 api context factory
 *
 * @author wxup
 */
public interface ApiRequestContextFactory {

    String AUTHENTICATE = "authenticate";

    String AUTHENTICATE_ID = "authenticateId";

    /**
     * 生成请求上下文的 工厂
     *
     * @param request http request
     * @return 请求上下文
     */
    Map<String, Object> factory(HttpServletRequest request);
}
