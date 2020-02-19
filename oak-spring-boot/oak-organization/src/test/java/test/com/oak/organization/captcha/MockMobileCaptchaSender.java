package test.com.oak.organization.captcha;

import com.wuxp.security.captcha.mobile.MobileCaptchaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * mock sender
 */
@Component
@Slf4j
public class MockMobileCaptchaSender implements MobileCaptchaSender {

    @Override
    public MobileCaptchaSenderResult send(String useType, String mobilePhone, String value) {

        log.info(" send {} {} {}", useType, mobilePhone, value);
        return new MobileCaptchaSenderResult();
    }
}
