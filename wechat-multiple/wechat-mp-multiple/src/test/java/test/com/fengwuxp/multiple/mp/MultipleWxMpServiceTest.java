package test.com.fengwuxp.multiple.mp;

import com.fengwuxp.wechat.multiple.WeChatMultipleProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultipleWeChatMpApplicationTest.class)
@Slf4j
@ActiveProfiles("test")
public class MultipleWxMpServiceTest {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ExampleService exampleService;

    @Autowired
    private WeChatMultipleProperties weChatMultipleProperties;

    @Test
    public void testWxMpService() {
        WxMpConfigStorage wxMpConfigStorage = wxMpService.getWxMpConfigStorage();
        log.debug("wxMpConfigStorage 1 {}", wxMpConfigStorage);
        wxMpConfigStorage = wxMpService.getWxMpConfigStorage();
        log.debug("wxMpConfigStorage 2 {}", wxMpConfigStorage);
        exampleService.examole();

    }
}
