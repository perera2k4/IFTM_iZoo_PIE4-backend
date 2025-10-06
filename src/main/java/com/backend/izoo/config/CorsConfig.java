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
        
        // Permite TODAS as origens - para desenvolvimento e produção
        configuration.setAllowedOrigins(Arrays.asList("*"));
        
        // Alternativa usando patterns (se precisar de mais controle):
        // configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Permite todos os métodos HTTP
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        
        // Permite todos os headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // IMPORTANTE: Quando allowedOrigins é "*", allowCredentials deve ser false
        configuration.setAllowCredentials(false);
        
        // Se você precisar de credenciais (JWT no header Authorization), use esta configuração alternativa:
        /*
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        */
        
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