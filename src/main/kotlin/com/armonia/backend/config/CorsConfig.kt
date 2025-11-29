package com.armonia.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        
        // Permitir todas las origenes (importante para Android Studio en desarrollo)
        config.allowedOriginPatterns = listOf("*")
        
        // Permitir todos los métodos HTTP
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
        
        // Permitir todos los headers
        config.allowedHeaders = listOf("*")
        
        // Permitir credenciales
        config.allowCredentials = true
        
        // Exponer headers de respuesta
        config.exposedHeaders = listOf("Authorization", "Content-Type")
        
        // Aplicar configuración a todas las rutas
        source.registerCorsConfiguration("/**", config)
        
        return CorsFilter(source)
    }
}
