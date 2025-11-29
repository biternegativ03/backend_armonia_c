package com.armonia.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

/**
 * Main function para ejecutar el backend de Armonia
 * 
 * Punto de entrada de la aplicación Spring Boot
 */
fun main(args: Array<String>) {
    println("🚀 Iniciando Armonia Backend...")
    println("📌 Base URL: http://localhost:8080")
    println("📌 API: http://localhost:8080/api/")
    
    runApplication<Application>(*args)
}
