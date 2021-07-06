package com.example.demo;

import com.example.demo.configurations.DatabaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class DemoApplication {
    @Autowired
    private DatabaseConfiguration databaseConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void display() {
        System.out.println("*** Read from Property file ***");
        System.out.printf("Url      : %s%n", databaseConfiguration.url);
        System.out.printf("Driver   : %s%n", databaseConfiguration.driverClassName);
        System.out.printf("Username : %s%n", databaseConfiguration.username);
        System.out.printf("Password : %s%n", databaseConfiguration.password);
    }
}
