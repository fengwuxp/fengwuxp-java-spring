package com.wuxp.security.example.context;

import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.security.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * mock ApiRequestContextFactory
 */
@Slf4j
public class MockApiRequestContextFactory implements ApiRequestContextFactory {


    @Cacheable(key = "#request.getSession().getId()", value = "REQUEST_CONTEXT")
    @Override
    public Map<String, Object> factory(HttpServletRequest request) {
        Map<String, Object> context = new HashMap<>();
        context.put("ip", IpAddressUtils.try2GetUserRealIPAddr(request));
        context.put("name", "张三");
        return context;

    }
}
