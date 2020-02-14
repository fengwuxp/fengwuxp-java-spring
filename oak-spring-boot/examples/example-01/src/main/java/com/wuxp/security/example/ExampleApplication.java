package com.wuxp.security.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.wuxp", "com.oak", "com.levin.commons.dao"},
        exclude={SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class ExampleApplication {

    public static void main(String[] args) {

        SpringApplication.run(ExampleApplication.class, args);
    }
}
