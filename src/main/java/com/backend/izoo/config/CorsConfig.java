package com.backend.izoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite origens específicas (você pode ajustar conforme necessário)
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:*",
            "http://127.0.0.1:*",
            "https://localhost:*",
            "https://127.0.0.1:*"
        ));
        
        // Para desenvolvimento, você pode usar:
        // configuration.setAllowedOrigins(Arrays.asList("*"));
        
        // Permite todos os métodos HTTP
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        
        // Permite todos os headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permite credenciais (cookies, authorization headers, etc.)
        configuration.setAllowCredentials(true);
        
        // Expõe headers para o frontend
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", 
            "Cache-Control", 
            "Content-Type",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Headers"
        ));
        
        // Define tempo de cache para requisições preflight
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}