#!/usr/bin/env pwsh
# Script para ejecutar el backend de Armonia

Write-Host "🎵 Armonia Backend - Executor" -ForegroundColor Cyan
Write-Host ""

# Verificar Java
Write-Host "Verificando Java..." -ForegroundColor Yellow
$javaVersion = java -version 2>&1
if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Java encontrado: $($javaVersion[0])" -ForegroundColor Green
} else {
    Write-Host "❌ Java no está instalado o no está en PATH" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Opciones de ejecución:" -ForegroundColor Cyan
Write-Host "1. Ejecutar con Gradle (desarrollo - con recarga automática)"
Write-Host "2. Compilar JAR y ejecutar (producción)"
Write-Host "3. Compilar y ejecutar JAR"
Write-Host ""

$option = Read-Host "Selecciona una opción (1-3)"

switch ($option) {
    "1" {
        Write-Host ""
        Write-Host "🚀 Compilando e iniciando con Gradle..." -ForegroundColor Cyan
        & ./gradlew.bat bootRun
    }
    "2" {
        Write-Host ""
        Write-Host "🔨 Compilando JAR..." -ForegroundColor Cyan
        & ./gradlew.bat build -x test --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "✅ JAR compilado correctamente" -ForegroundColor Green
            Write-Host ""
            Write-Host "📂 Ejecutando JAR..." -ForegroundColor Cyan
            java -jar build/libs/armonia-backend-0.1.0-SNAPSHOT.jar
        } else {
            Write-Host "❌ Error al compilar" -ForegroundColor Red
            exit 1
        }
    }
    "3" {
        Write-Host ""
        Write-Host "🔨 Compilando JAR..." -ForegroundColor Cyan
        & ./gradlew.bat build -x test --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host ""
            Write-Host "✅ JAR compilado correctamente" -ForegroundColor Green
            Write-Host ""
            Write-Host "🚀 Iniciando backend..." -ForegroundColor Cyan
            java -jar build/libs/armonia-backend-0.1.0-SNAPSHOT.jar
        } else {
            Write-Host "❌ Error al compilar" -ForegroundColor Red
            exit 1
        }
    }
    default {
        Write-Host "❌ Opción inválida" -ForegroundColor Red
        exit 1
    }
}
