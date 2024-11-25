package com.restApi.RestApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Erlaubt CORS für alle Anfragen, die auf das Backend zugreifen wollen
        registry.addMapping("/**")  // Alle Endpunkte erlauben
                .allowedOrigins("http://localhost:5173") // Nur vom Frontend (React) erlaubt
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //  HTTP-Methoden 
                .allowedHeaders("*") // Alle Header erlauben
                .allowCredentials(true); // Cookies oder Header wie Authorization
    }
}
