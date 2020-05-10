package test.com.wuxp.security.example.config;


import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.TestAnnotationApiOperationSource;
import com.wuxp.api.interceptor.TestMethodApiInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {


    @Bean
    public ApiInterceptor apiInterceptor() {
        return new TestMethodApiInterceptor();
    }

    @Bean
    public ApiOperationSource apiOperationSource() {
        return new TestAnnotationApiOperationSource();
    }


}
