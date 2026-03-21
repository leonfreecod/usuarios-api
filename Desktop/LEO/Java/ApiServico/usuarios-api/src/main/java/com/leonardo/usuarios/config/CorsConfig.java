package com.leonardo.usuarios.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Substituímos pelos domínios específicos para maior segurança
                .allowedOrigins("http://localhost:3000", "https://leofe.com.br", "https://www.leofe.com.br", "null")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // Deixe como true se pretender usar autenticação via Cookies/Sessions no futuro
                .allowCredentials(true)
                .maxAge(3600);
    }
}