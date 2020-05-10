package test.com.wuxp.security.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import test.com.wuxp.security.example.config.TestConfig;

import java.io.File;


@SpringBootApplication(
        scanBasePackages = {"com.wuxp", "test.com.wuxp", "com.levin.commons.dao"})
@Import(TestConfig.class)
@EntityScan(basePackages = {"com.wuxp", "com.oak","com.oaknt"})
@Configuration
public class ExampleApplicationTest {

    public static void main(String[] args) {

        System.out.println(ExampleApplicationTest.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

        SpringApplication.run(ExampleApplicationTest.class, args);

    }

}
