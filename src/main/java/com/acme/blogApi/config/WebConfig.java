package com.acme.blogApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST")
                .allowedHeaders("*");

        registry.addMapping("/user/{username}/auth")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET")
                .allowedHeaders("*");

        registry.addMapping("/postagem")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*");

        registry.addMapping("/postagem/{id}")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "PUT", "DELETE")
                .allowedHeaders("*");

        registry.addMapping("/postagem/{postId}/curtir/{userId}")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("PUT")
                .allowedHeaders("*");
    }
}
