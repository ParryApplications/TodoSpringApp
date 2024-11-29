package org.parryapplications.spring.todoproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CorsMappingConfig {
    //Global CORS Mapping:
    @Bean
    public WebMvcConfigurer webMvcConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
