package test.com.fengwuxp.multiple.mp;

import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class MockWeChatAppIdProvider implements WeChatAppIdProvider {

    @Override
    public String getTargetAppId() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return RandomStringUtils.randomAlphabetic(32);
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String appId = request.getParameter("appId");
        return appId == null ? "mockk_appId" : appId;
    }
}
