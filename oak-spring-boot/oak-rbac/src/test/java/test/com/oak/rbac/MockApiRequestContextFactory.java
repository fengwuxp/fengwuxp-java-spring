package test.com.oak.rbac;

import com.wuxp.api.context.ApiRequestContextFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@Component
public class MockApiRequestContextFactory implements ApiRequestContextFactory {

    @Override
    public Map<String, Object> factory(HttpServletRequest request) {
        return Collections.emptyMap();
    }
}
