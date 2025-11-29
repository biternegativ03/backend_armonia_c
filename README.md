# Armonia Backend (Spring Boot + MongoDB)

Backend para cálculo y seguimiento de ciclo menstrual que complementa la app Android.

## Stack
- Kotlin + Spring Boot 3
- MongoDB (Spring Data)
- Endpoints REST para ciclos, preferencias y predicciones

## Requisitos
- JDK 17
- Docker (opcional para Mongo local)
- MongoDB URI (por defecto `mongodb://localhost:27017/armonia`)

## Ejecutar
```bash
# Levantar Mongo con docker compose
cd backend
docker compose up -d

# Ejecutar la app
./gradlew bootRun
```

En Windows PowerShell:
```powershell
cd backend; docker compose up -d; ./gradlew.bat bootRun
```

### Variables de entorno
- `MONGODB_URI` URI de conexión (override de valor por defecto)

## Endpoints Principales (prefijo /api)
- `GET /api/cycles` Lista de inicios de periodo
- `POST /api/cycles` Registrar nuevo periodo `{"date":"2025-11-01"}`
- `GET /api/cycles/next-period` Predicción próximo periodo
- `GET /api/cycles/ovulation` Día de ovulación
- `GET /api/cycles/fertile-window` Ventana fértil
- `GET /api/cycles/day-of-cycle?date=2025-11-29` Día relativo al ciclo actual
- `GET /api/prefs` Obtener preferencias ciclo
- `PUT /api/prefs` Actualizar preferencias
- `POST /api/ovulation-observed` Registrar evidencia `{ "opkDate":"2025-11-15" }`

## Retrofit Interface (Android)
```kotlin
interface CycleApi {
    @GET("cycles") suspend fun getCycleStarts(): List<CycleStartDto>
    @POST("cycles") suspend fun addCycle(@Body req: PeriodStartRequest)
    @GET("cycles/next-period") suspend fun nextPeriod(): CyclePredictionResponse?
    @GET("cycles/ovulation") suspend fun ovulationDay(): CyclePredictionResponse?
    @GET("cycles/fertile-window") suspend fun fertileWindow(): FertileWindowResponse?
    @GET("cycles/day-of-cycle") suspend fun dayOfCycle(@Query("date") date: String): DayOfCycleResponse
    @GET("prefs") suspend fun getPrefs(): UserPrefsResponse?
    @PUT("prefs") suspend fun updatePrefs(@Body req: UpdatePrefsRequest): UserPrefsResponse
    @POST("ovulation-observed") suspend fun setOvulation(@Body req: OvulationObservationRequest)
}
```

## Cálculos
Se usan reglas simplificadas del `CycleCalculator`:
- Próximo período: `lastPeriodStart + avgCycleLength`
- Ovulación: `lastPeriodStart + (avgCycleLength - lutealLength)` o evidencia observada
- Ventana fértil: 2 días antes a 1 día después de ovulación

## Tests
Agregar tests en `src/test/kotlin` para validar cálculos.

## Licencia
Uso interno del proyecto Armonia.
