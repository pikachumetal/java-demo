package com.example.demo;

import com.example.demo.configurations.DatabaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
@Slf4j
public class DemoApplication {
    @Autowired
    private DatabaseConfiguration databaseConfiguration;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "problem");
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void display() {
        log.info("*** Read from Property file ***");
        log.info("Url      : " + databaseConfiguration.url);
        log.info("Driver   : " + databaseConfiguration.driverClassName);
        log.info("Username : " + databaseConfiguration.username);
        log.info("Password : " + databaseConfiguration.password);
    }

}
