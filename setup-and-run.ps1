# Script de configuración y ejecución del backend Armonia
# Ejecutar con: .\setup-and-run.ps1

Write-Host "🌸 Armonia Backend - Setup Script" -ForegroundColor Magenta
Write-Host "=================================" -ForegroundColor Magenta
Write-Host ""

# Verificar Java
Write-Host "Verificando Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "✅ Java encontrado: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "❌ Java no encontrado. Instala Java 17 o superior." -ForegroundColor Red
    Write-Host "Descarga desde: https://adoptium.net/" -ForegroundColor Cyan
    exit 1
}

Write-Host ""

# Verificar MongoDB
Write-Host "Verificando MongoDB..." -ForegroundColor Yellow
$mongoRunning = $false

# Intentar conectar a MongoDB local
try {
    $mongoTest = Test-NetConnection -ComputerName localhost -Port 27017 -WarningAction SilentlyContinue
    if ($mongoTest.TcpTestSucceeded) {
        Write-Host "✅ MongoDB está corriendo en localhost:27017" -ForegroundColor Green
        $mongoRunning = $true
    }
} catch {
    # Ignorar error
}

if (-not $mongoRunning) {
    Write-Host "⚠️  MongoDB no detectado en localhost:27017" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Opciones:" -ForegroundColor Cyan
    Write-Host "1. Instalar MongoDB: https://www.mongodb.com/try/download/community" -ForegroundColor White
    Write-Host "2. Usar Docker: docker compose up -d" -ForegroundColor White
    Write-Host "3. Usar MongoDB Atlas (nube): https://www.mongodb.com/cloud/atlas" -ForegroundColor White
    Write-Host ""
    
    $continue = Read-Host "¿Continuar de todas formas? (y/n)"
    if ($continue -ne "y") {
        Write-Host "Setup cancelado." -ForegroundColor Red
        exit 1
    }
}

Write-Host ""

# Verificar Gradle
Write-Host "Verificando Gradle..." -ForegroundColor Yellow
$useGradlew = $false

if (Test-Path ".\gradlew.bat") {
    Write-Host "✅ Gradle Wrapper encontrado" -ForegroundColor Green
    $useGradlew = $true
} else {
    try {
        $gradleVersion = gradle --version 2>&1 | Select-String "Gradle"
        Write-Host "✅ Gradle encontrado: $gradleVersion" -ForegroundColor Green
    } catch {
        Write-Host "❌ Gradle no encontrado" -ForegroundColor Red
        Write-Host ""
        Write-Host "¿Deseas generar el Gradle Wrapper? (Necesitas Gradle instalado)" -ForegroundColor Cyan
        Write-Host "Si no tienes Gradle, instálalo desde: https://gradle.org/install/" -ForegroundColor White
        Write-Host ""
        
        $response = Read-Host "¿Generar Gradle Wrapper? (y/n)"
        if ($response -eq "y") {
            Write-Host "Generando Gradle Wrapper..." -ForegroundColor Yellow
            gradle wrapper
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✅ Gradle Wrapper generado" -ForegroundColor Green
                $useGradlew = $true
            } else {
                Write-Host "❌ Error al generar Gradle Wrapper" -ForegroundColor Red
                exit 1
            }
        } else {
            Write-Host "Setup cancelado." -ForegroundColor Red
            exit 1
        }
    }
}

Write-Host ""
Write-Host "=================================" -ForegroundColor Magenta
Write-Host "🚀 Iniciando Backend..." -ForegroundColor Green
Write-Host "=================================" -ForegroundColor Magenta
Write-Host ""
Write-Host "El servidor arrancará en: http://localhost:8080" -ForegroundColor Cyan
Write-Host "Endpoints disponibles en: http://localhost:8080/api" -ForegroundColor Cyan
Write-Host ""
Write-Host "Para conectar desde Android:" -ForegroundColor Yellow
Write-Host "  - Emulador: http://10.0.2.2:8080/api" -ForegroundColor White
Write-Host "  - Dispositivo físico: http://[TU_IP]:8080/api" -ForegroundColor White
Write-Host ""
Write-Host "Presiona Ctrl+C para detener el servidor" -ForegroundColor Yellow
Write-Host ""

# Ejecutar el backend
if ($useGradlew) {
    .\gradlew.bat bootRun
} else {
    gradle bootRun
}
