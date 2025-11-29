# Armonia Backend - Configuración para Android Studio

## 🚀 Configuración Completada

Se han realizado los siguientes cambios para conectar con Android:

✅ **Configuración CORS** - Permite conexiones desde Android Studio
✅ **Endpoints REST listos** - Todos los endpoints configurados en `/api`
✅ **Validaciones** - DTOs con validación de datos
✅ **Serialización JSON** - Jackson configurado para fechas

## 📋 Requisitos Previos

Para ejecutar este backend necesitas:

1. **Java 17** o superior
2. **MongoDB** (puede ser local o Docker)
3. **Gradle** (o usar el wrapper incluido en el proyecto)

## 🔧 Instalación

### Opción 1: Instalar Gradle Wrapper (Recomendado)

Si no tienes Gradle, puedes generar el wrapper:

```powershell
# Si tienes Gradle instalado globalmente:
gradle wrapper

# O descarga Gradle desde: https://gradle.org/install/
```

### Opción 2: Usar Gradle instalado globalmente

Descarga e instala Gradle desde: https://gradle.org/install/

### Opción 3: Usar Android Studio

Android Studio viene con Gradle incluido. Puedes:
1. Abrir este proyecto en Android Studio (File > Open)
2. Ejecutar desde la terminal de Android Studio: `./gradlew bootRun`

## 🗄️ Configurar MongoDB

### Opción 1: MongoDB con Docker (Recomendado)

```powershell
# Instalar Docker Desktop desde: https://www.docker.com/products/docker-desktop/

# Luego ejecutar:
docker compose up -d
```

### Opción 2: MongoDB local

1. Descargar e instalar MongoDB desde: https://www.mongodb.com/try/download/community
2. Iniciar el servicio MongoDB
3. El backend se conectará automáticamente a `mongodb://localhost:27017/armonia`

### Opción 3: MongoDB Atlas (Nube - Gratis)

1. Crear cuenta en https://www.mongodb.com/cloud/atlas
2. Crear un cluster gratuito
3. Obtener la connection string
4. Establecer la variable de entorno:
   ```powershell
   $env:MONGODB_URI="mongodb+srv://usuario:password@cluster.mongodb.net/armonia"
   ```

## ▶️ Ejecutar el Backend

Una vez tengas MongoDB corriendo:

```powershell
# Con Gradle Wrapper (si lo instalaste):
./gradlew bootRun

# Con Gradle global:
gradle bootRun

# Desde Android Studio:
# Terminal > ./gradlew bootRun
```

El servidor arrancará en: **http://localhost:8080**

## 🌐 Endpoints Disponibles

Base URL: `http://localhost:8080/api`

### Ciclos Menstruales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/cycles` | Lista todos los inicios de ciclo registrados |
| POST | `/cycles` | Registra un nuevo inicio de periodo |
| GET | `/cycles/next-period` | Predice la próxima fecha de periodo |
| GET | `/cycles/ovulation` | Predice el día de ovulación |
| GET | `/cycles/fertile-window` | Obtiene la ventana fértil |
| GET | `/cycles/day-of-cycle?date=YYYY-MM-DD` | Obtiene el día del ciclo para una fecha |

### Preferencias de Usuario

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/prefs` | Obtiene las preferencias del usuario |
| PUT | `/prefs` | Actualiza las preferencias del usuario |

### Observaciones de Ovulación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/ovulation-observed` | Registra observaciones de ovulación (OPK, BBT) |

## 📱 Conectar desde Android

### 1. Emulador Android

Si usas el emulador de Android Studio, usa:
```
http://10.0.2.2:8080/api
```

### 2. Dispositivo Físico

Si usas un dispositivo físico conectado por WiFi:
```
http://TU_IP_LOCAL:8080/api
```

Para obtener tu IP local:
```powershell
ipconfig
# Busca "IPv4 Address" en tu adaptador de red WiFi
```

### 3. Configuración en Retrofit (Android)

```kotlin
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/api/" // Para emulador
    // private const val BASE_URL = "http://192.168.1.X:8080/api/" // Para dispositivo físico
    
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

## 🧪 Probar los Endpoints

### Con cURL (PowerShell)

```powershell
# Obtener preferencias
Invoke-RestMethod -Uri "http://localhost:8080/api/prefs" -Method GET

# Registrar nuevo periodo
$body = @{ date = "2025-11-29" } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/cycles" -Method POST -Body $body -ContentType "application/json"

# Actualizar preferencias
$body = @{ 
    lastPeriodStart = "2025-11-29"
    avgCycleLength = 28
    lutealLength = 14
    mensesLength = 5
} | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/api/prefs" -Method PUT -Body $body -ContentType "application/json"

# Obtener predicción de próximo periodo
Invoke-RestMethod -Uri "http://localhost:8080/api/cycles/next-period" -Method GET
```

### Con Postman

1. Descargar Postman: https://www.postman.com/downloads/
2. Importar la colección de requests
3. Probar cada endpoint

## 🐛 Solución de Problemas

### Error: "Connection refused"
- Verifica que MongoDB esté corriendo
- Verifica que el puerto 8080 no esté ocupado

### Error: "No gradlew found"
- Ejecuta `gradle wrapper` para generar los archivos wrapper
- O usa `gradle bootRun` directamente

### Error de conexión desde Android
- Verifica que uses `10.0.2.2` en el emulador
- Verifica que el firewall permita conexiones al puerto 8080
- Si usas dispositivo físico, verifica que esté en la misma red WiFi

### MongoDB no arranca con Docker
- Verifica que Docker Desktop esté instalado e iniciado
- Verifica que el puerto 27017 no esté ocupado
- Prueba: `docker ps` para ver si el contenedor está corriendo

## 📄 Estructura de DTOs

### PeriodStartRequest
```json
{
  "date": "2025-11-29"
}
```

### UpdatePrefsRequest
```json
{
  "lastPeriodStart": "2025-11-29",
  "avgCycleLength": 28,
  "lutealLength": 14,
  "mensesLength": 5
}
```

### OvulationObservationRequest
```json
{
  "opkDate": "2025-12-10",
  "bbtRiseDate": "2025-12-11"
}
```

## 🔒 Seguridad

**IMPORTANTE**: La configuración CORS actual permite todas las conexiones. 
Para producción, debes restringir los orígenes permitidos en `CorsConfig.kt`.

## 📞 Soporte

Si encuentras problemas:
1. Verifica los logs del servidor en la terminal
2. Verifica que MongoDB esté corriendo: `docker ps` o servicio local
3. Prueba los endpoints con Postman antes de probar desde Android

---

**¡Todo listo para conectar desde Android Studio! 🎉**
