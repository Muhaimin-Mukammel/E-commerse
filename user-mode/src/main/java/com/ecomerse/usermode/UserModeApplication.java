package com.ecomerse.usermode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ecomerse.usermode",
        "config",
        "repository",
        "service",
        "controller"
})
@EntityScan(basePackages = "entity")
public class UserModeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserModeApplication.class, args);
    }

}
