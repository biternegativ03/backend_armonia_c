package com.armonia.backend

import org.springframework.boot.SpringApplication

/**
 * Main class para ejecutar el backend de Armonia.
 * 
 * Este archivo puede ser ejecutado directamente desde VS Code
 * usando el botón de "Play" o con Ctrl+F5.
 * 
 * El backend se iniciará en: http://localhost:8080
 * API base: http://localhost:8080/api/
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("🚀 Iniciando Armonia Backend...")
        println("📌 Base URL: http://localhost:8080")
        println("📌 API: http://localhost:8080/api/")
        
        SpringApplication.run(Application::class.java, *args)
        
        println("✅ Backend iniciado correctamente")
    }
}
