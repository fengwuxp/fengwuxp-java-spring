package test.com.fengwuxp.multiple.miniapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;


@SpringBootApplication(
        scanBasePackages = {"com.fengwuxp", "test.com.fengwuxp"})
@Import(TestConfig.class)
@Configuration
//@EnableWebMvc
public class MultipleWeChatMiniAppApplicationTest {

    public static void main(String[] args) {

        System.out.println(MultipleWeChatMiniAppApplicationTest.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

        SpringApplication.run(MultipleWeChatMiniAppApplicationTest.class, args);

    }

}
