package com.project.elite_construcoes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite todas as rotas
                .allowedOrigins("http://127.0.0.1:5500") // Adicione a origem permitida
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*"); // Todos os cabeçalhos
    }


}
