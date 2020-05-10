package com.wuxp.security.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wxup
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.levin.commons.dao",
                "com.wuxp",
                "com.oak"
        }
)
@EntityScan(basePackages = {"com.wuxp", "com.oak", "com.oaknt"})
//@EnableAspectJAutoProxy()
public class Example01Application {

    public static void main(String[] args) {

        SpringApplication.run(Example01Application.class, args);
    }
}
