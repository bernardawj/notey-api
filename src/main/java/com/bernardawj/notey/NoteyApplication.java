package com.bernardawj.notey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:message.properties")
})
@SpringBootApplication
public class NoteyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteyApplication.class, args);
    }

}
