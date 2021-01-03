package test.com.fengwuxp.multiple.mp;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExampleService {
    @Autowired
    private WxMpService wxMpService;

    public void examole() {
        log.debug("{}", wxMpService);
    }
}
