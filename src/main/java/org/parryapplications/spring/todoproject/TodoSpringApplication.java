package org.parryapplications.spring.todoproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TodoSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoSpringApplication.class, args);
    }
}