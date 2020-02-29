package test.com.fengwuxp.multiple.mp.controller;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestWxMpMultipleController {

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/config")
    public WxMpConfigStorage getWxMpConfigStorage() {
        try {
            Thread.sleep(RandomUtils.nextInt() % 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WxMpConfigStorage wxMpConfigStorage = wxMpService.getWxMpConfigStorage();
        return wxMpConfigStorage;
    }
}
