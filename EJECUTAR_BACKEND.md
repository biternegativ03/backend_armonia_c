# 🎵 Armonia Backend - Guía de Ejecución

## Requisitos Previos

- **Java 17+** instalado
- **Gradle** (incluido en el proyecto)
- **MongoDB** corriendo localmente en `mongodb://localhost:27017`

## ▶️ Opciones para Ejecutar el Backend

### Opción 1: Desde VS Code (Recomendado)

1. Abre el archivo `src/main/kotlin/com/armonia/backend/Application.kt`
2. Presiona el botón **"Run"** que aparece sobre la función `main`
3. Espera a que compile y se inicie

**O también puedes:**
- Presionar `Ctrl+F5` para ejecutar en debug
- Ir a **Run → Run Without Debugging**

### Opción 2: Desde la Terminal

```powershell
# Opción A: Con Gradle (desarrollo)
./gradlew.bat bootRun

# Opción B: Con JAR compilado (producción)
./gradlew.bat build -x test
java -jar build/libs/armonia-backend-0.1.0-SNAPSHOT.jar
```

### Opción 3: Con Tarea de VS Code

1. Presiona `Ctrl+Shift+B` para ejecutar la tarea de build
2. Luego ejecuta la tarea "Run Backend" desde la paleta de comandos (`Ctrl+Shift+P`)

## 🌐 Endpoints Disponibles

Cuando el backend esté corriendo, puedes probar estos endpoints:

```bash
# Obtener ciclos registrados
curl http://localhost:8080/api/cycles

# Registrar nuevo ciclo
curl -X POST http://localhost:8080/api/cycles \
  -H "Content-Type: application/json" \
  -d '{"date":"2025-11-29"}'

# Obtener próximo período
curl http://localhost:8080/api/cycles/next-period

# Obtener día de ovulación
curl http://localhost:8080/api/cycles/ovulation

# Obtener ventana fértil
curl http://localhost:8080/api/cycles/fertile-window

# Obtener día del ciclo
curl "http://localhost:8080/api/cycles/day-of-cycle?date=2025-11-29"

# Obtener preferencias de usuario
curl http://localhost:8080/api/prefs

# Actualizar preferencias
curl -X PUT http://localhost:8080/api/prefs \
  -H "Content-Type: application/json" \
  -d '{"avgCycleLength":28,"lutealLength":14,"mensesLength":5}'

# Registrar ovulación observada
curl -X POST http://localhost:8080/api/ovulation-observed \
  -H "Content-Type: application/json" \
  -d '{"opkDate":"2025-12-10","bbtRiseDate":"2025-12-11"}'
```

## 📊 Logs

Los logs se mostrarán en la terminal integrada de VS Code o en la consola donde ejecutes el comando.

## ⚠️ Troubleshooting

### Error: "MongoDB connection refused"
- Asegúrate de que MongoDB esté corriendo
- En Windows con MongoDB instalado localmente, ejecuta:
  ```powershell
  mongod
  ```

### Error: "Port 8080 already in use"
- Otro proceso está usando el puerto 8080
- Para cambiar el puerto, edita `src/main/resources/application.yml`:
  ```yaml
  server:
    port: 8081  # Cambia a otro puerto
  ```

### Error al compilar
- Limpia el caché de Gradle:
  ```powershell
  ./gradlew.bat clean build -x test
  ```

## 📱 Conexión desde Android

La app Android está configurada para conectar a:
- **Emulador**: `http://10.0.2.2:8080/api/`
- **Dispositivo físico**: `http://TU_IP_LOCAL:8080/api/`

Para cambiar la URL, edita `RetrofitClient.kt` en la app Android.

## 🔄 Recarga en Caliente

Para desarrollo, puedes usar DevTools de Spring Boot:
1. El backend detectará cambios en archivos
2. Reinicia la aplicación manualmente cuando hagas cambios grandes

