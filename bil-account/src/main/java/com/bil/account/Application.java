package com.bil.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * start
 *
 * @author bob
 */
@SpringBootApplication
@ComponentScan("com.bil")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
