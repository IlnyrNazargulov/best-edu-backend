package ru.ilnyrdiplom.bestedu.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@SpringBootApplication
@PropertySource("classpath:web.properties")
public class BestEduApplication {
    public static void main(String[] args) {
        SpringApplication.run(BestEduApplication.class, args);
    }
}
