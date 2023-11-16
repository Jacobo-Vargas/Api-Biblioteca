package com.example.Biblioteca.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Esta clase se realiza para permitir los metodos o endpoints que interactuan
     * @param registry
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Mapea todas las rutas
                .allowedOrigins("*")  // Permite todas las solicitudes desde cualquier origen
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permite los métodos HTTP especificados
                .allowedHeaders("*");
    }
}

