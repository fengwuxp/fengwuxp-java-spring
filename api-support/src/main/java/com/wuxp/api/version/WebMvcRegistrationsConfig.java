package com.wuxp.api.version;

import com.wuxp.api.configuration.WuxpApiSupportProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author wuxp
 */
@Configuration
@ConditionalOnProperty(prefix = WuxpApiSupportProperties.PREFIX, name = "enabled-api-version", havingValue = "true")
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new MultiVersionRequestMappingHandlerMapping();
    }
}
