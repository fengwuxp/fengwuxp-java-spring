package test.com.fengwuxp.multiple.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestWxMpMultipleController {

    @Autowired
    private WxMaService wxMpService;

    @RequestMapping("/config")
    public WxMaConfig getWxMpConfigStorage() {
        try {
            Thread.sleep(RandomUtils.nextInt() % 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WxMaConfig wxMaConfig = wxMpService.getWxMaConfig();
        return wxMaConfig;
    }
}
