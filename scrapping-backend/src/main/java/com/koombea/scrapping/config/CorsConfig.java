package com.koombea.scrapping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/auth/token") // Reemplaza con la ruta de tu solicitud token
                        .allowedOrigins("http://localhost:4200") // Origen de tu aplicación Angular
                        .allowedMethods("POST") // Permitir solo el método POST
                        .allowedHeaders("Authorization"); // Permitir el encabezado de autorización
            }
        };
    }
}