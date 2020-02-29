package test.com.fengwuxp.multiple.miniapp;

import com.fengwuxp.multiple.wechat.WeChatAppIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public WeChatAppIdProvider mockWeChatAppIdProvider() {
        return new MockWeChatAppIdProvider();
    }

    @Bean
    public MockWeChatMpConfigStorageProvider weChatMpConfigStorageProvider() {

        return new MockWeChatMpConfigStorageProvider();
    }
}
