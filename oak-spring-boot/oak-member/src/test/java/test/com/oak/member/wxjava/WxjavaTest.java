package test.com.oak.member.wxjava;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.binarywang.spring.starter.wxjava.miniapp.config.WxMaAutoConfiguration;
import com.binarywang.spring.starter.wxjava.mp.config.WxMpAutoConfiguration;
import com.binarywang.spring.starter.wxjava.mp.config.WxMpServiceAutoConfiguration;
import com.binarywang.spring.starter.wxjava.mp.config.WxMpStorageAutoConfiguration;
import com.oak.api.services.app.AppAuthService;
import com.oak.member.management.third.ThirdService;
import com.oak.member.management.third.req.GetWxMaSessionReq;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.member.OakApplicationTest;

@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {OakApplicationTest.class})
public class WxjavaTest {


    /**
     * @see WxMaAutoConfiguration
     */
    @Autowired
    private WxMaService wxMaService;

    /**
     * @see WxMpAutoConfiguration
     * @see WxMpServiceAutoConfiguration
     * @see WxMpStorageAutoConfiguration
     */
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ThirdService thirdService;

    @Test
    public void testWxMinapp() {
        GetWxMaSessionReq req = new GetWxMaSessionReq();
        req.setCode("1245");
        System.out.println(thirdService.getWxMaSessionInfo(req));

    }
}
