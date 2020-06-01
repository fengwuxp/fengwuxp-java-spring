package test.com.fengwuxp.multiple.miniapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultipleWeChatMiniAppApplicationTest.class)
@Slf4j
@ActiveProfiles("test")
public class MultipleWxMpServiceTest {

    @Autowired
    private WxMaService wxMaService;


    @Test
    public void testWxMpService() {
        WxMaConfig wxMaConfig = wxMaService.getWxMaConfig();
        log.debug("wxMpConfigStorage 1 {}", wxMaConfig);
        wxMaConfig = wxMaService.getWxMaConfig();
        log.debug("wxMpConfigStorage 2 {}", wxMaConfig);

    }
}
