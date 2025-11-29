# 🔌 Guía de Conexión Backend - Android App

## 📋 Estado Actual

✅ **Backend**: Completamente funcional con API REST  
✅ **Android App**: Configurada con Retrofit para consumir la API  
✅ **DTOs**: Sincronizados y compatibles con serialización JSON  
✅ **Adaptador LocalDate**: Implementado en Moshi  

## 🚀 Paso 1: Iniciar el Backend

Desde la carpeta `armonia_backend`:

```powershell
# Opción A: Ejecutar script interactivo
./run-backend.ps1

# Opción B: Directamente desde VS Code
# 1. Abre Application.kt
# 2. Presiona el botón "Run" o Ctrl+F5

# Opción C: Desde terminal
./gradlew.bat bootRun
```

El backend estará disponible en: **http://localhost:8080**

## 📱 Paso 2: Conectar desde la App Android

### Si usas el Emulador Android:
La app ya está configurada correctamente en `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/api/"
```

Este es el alias de localhost desde el emulador.

### Si usas un Dispositivo Físico:
1. Obtén tu IP local (en la máquina donde corre el backend):
   ```powershell
   ipconfig
   # Busca la IPv4 de tu red (ej: 192.168.x.x)
   ```

2. Edita `RetrofitClient.kt` en la app Android:
   ```kotlin
   private const val BASE_URL = "http://192.168.X.X:8080/api/"  // Reemplaza con tu IP
   ```

3. Compila y ejecuta la app en tu dispositivo

## 🧪 Paso 3: Pruebas de Conexión

### Con cURL (desde PowerShell):
```powershell
# Test 1: Verificar que el servidor está corriendo
Invoke-WebRequest -Uri "http://localhost:8080/api/cycles" -Method GET

# Test 2: Registrar un ciclo
$body = @{ date = "2025-11-29" } | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8080/api/cycles" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body

# Test 3: Obtener próximo período
Invoke-WebRequest -Uri "http://localhost:8080/api/cycles/next-period" -Method GET
```

### Desde la App Android:
1. Abre la app en el emulador/dispositivo
2. Intenta registrar un nuevo ciclo
3. Los datos se sincronizarán con el backend automáticamente

## 📊 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cycles` | Obtener ciclos registrados |
| POST | `/api/cycles` | Registrar nuevo ciclo |
| GET | `/api/cycles/next-period` | Predecir próximo período |
| GET | `/api/cycles/ovulation` | Obtener día de ovulación |
| GET | `/api/cycles/fertile-window` | Obtener ventana fértil |
| GET | `/api/cycles/day-of-cycle?date=YYYY-MM-DD` | Obtener día del ciclo |
| GET | `/api/prefs` | Obtener preferencias |
| PUT | `/api/prefs` | Actualizar preferencias |
| POST | `/api/ovulation-observed` | Registrar ovulación observada |

## 🔄 Sincronización de Datos

**Flow de datos:**
```
App Android (Retrofit Client)
    ↓
Realiza petición HTTP
    ↓
Backend Spring Boot (Port 8080)
    ↓
Procesa con CycleService
    ↓
Almacena en MongoDB
    ↓
Retorna JSON serializado
    ↓
App Android (Moshi deserializa)
    ↓
Guarda en Room Database
```

## ⚙️ Configuración de Base de Datos

### Backend (MongoDB)
Por defecto: `mongodb://localhost:27017/armonia`

Si necesitas cambiar:
1. Edita `src/main/resources/application.yml`
2. Cambia la URL de MongoDB:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://usuario:contraseña@host:puerto/armonia
   ```

### App Android (Room)
Base de datos local en el dispositivo:
- Base de datos: `armonia_database.db`
- Repositorio: `RemoteCycleRepository.kt`

## 🐛 Troubleshooting

### Error 403 - Permiso denegado
- Verifica que MongoDB esté corriendo
- Revisa los logs del backend

### Error "Connection refused"
- Backend no está iniciado
- Ejecuta: `./gradlew.bat bootRun`

### Error de serialización JSON
- Asegúrate de que `LocalDateAdapter` está registrado en Moshi
- Verificar que las DTOs tengan `@JsonClass(generateAdapter = true)`

### La app no se conecta
- Verifica la URL en `RetrofitClient.kt`
- Para emulador: usa `10.0.2.2`
- Para dispositivo: usa tu IP local
- Asegúrate de que el puerto 8080 esté abierto

## 📞 Support

Si encuentras problemas:
1. Revisa los logs del backend (terminal)
2. Revisa los logs de Android (Logcat)
3. Verifica la conectividad de red
4. Intenta un request simple con cURL primero

