package com.wuxp.security.example.context;

import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.basic.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.wuxp.api.ApiRequest.*;


/**
 * mock ApiRequestContextFactory
 */
@Slf4j
public class MockApiRequestContextFactory implements ApiRequestContextFactory {

    private static Map<String, Object> FIXED_MAP = new HashMap<>();

    static {
        FIXED_MAP.put(APP_ID_KEY, "111");
        FIXED_MAP.put(APP_SECRET_KEY, "222");
        FIXED_MAP.put(NONCE_STR_KEY, RandomStringUtils.randomAlphabetic(32));
        FIXED_MAP.put(TIME_STAMP, System.currentTimeMillis());
        FIXED_MAP.put(CHANNEL_CODE, "mock");
        FIXED_MAP.put("apiSignature", RandomStringUtils.randomAlphabetic(32));
//        FIXED_MAP.put("ip", "127.0.0.1");
    }

    @Cacheable(key = "#request.getSession().getId()", value = "REQUEST_CONTEXT")
    @Override
    public Map<String, Object> factory(HttpServletRequest request) {
        Map<String, Object> context = new HashMap<>();
        context.put("ip", IpAddressUtils.try2GetUserRealIPAddr(request));
        context.put("name", "张三");
        context.putAll(FIXED_MAP);
        return context;
    }
}




