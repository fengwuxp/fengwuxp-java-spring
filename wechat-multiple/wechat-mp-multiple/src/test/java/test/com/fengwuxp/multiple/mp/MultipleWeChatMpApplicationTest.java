package test.com.fengwuxp.multiple.mp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;


@SpringBootApplication(
        scanBasePackages = {"com.fengwuxp", "test.com.fengwuxp"})
@Import(TestConfig.class)
@Configuration
public class MultipleWeChatMpApplicationTest {

    public static void main(String[] args) {

        System.out.println(MultipleWeChatMpApplicationTest.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

        SpringApplication.run(MultipleWeChatMpApplicationTest.class, args);

    }

}
