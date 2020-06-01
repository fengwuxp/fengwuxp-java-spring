package test.com.fengwuxp.multiple.miniapp;


import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

@Slf4j
public class ParallelRequestTest {


    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void requestTest() {

        Thread[] threads = new Thread[1000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = request(i + "");
        }
        for (Thread thread : threads) {
            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
        }

        try {
            Thread.sleep(threads.length * 400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private Thread request(String appId) {
        String url = MessageFormat.format("http://localhost:8080/test/config?appId={0}", appId);
        return new Thread(() -> {
            try {
                Thread.sleep(RandomUtils.nextInt() % 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String json = responseEntity.getBody();
            WxMaConfig body = JSON.parseObject(json, WxMaDefaultConfigImpl.class);
            Assert.assertNotNull("response is null", body);
            String message = MessageFormat.format("request appId = {0} ,response app id = {1}",
                    appId, body.getAppid());
            Assert.assertEquals(message, appId,
                    body.getAppid());
            log.info(message);
        });
    }
}
