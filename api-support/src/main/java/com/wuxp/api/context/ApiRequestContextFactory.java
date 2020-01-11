package com.wuxp.api.context;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 生成 api context factory
 *
 * @param <T> context
 */
public interface ApiRequestContextFactory {

    String AUTHENTICATE = "authenticate";

    String AUTHENTICATE_ID = "authenticateId";

    Map<String, Object> factory(HttpServletRequest request);
}
