# Armonia Backend - Guía de Referencia Rápida

## 🚀 Inicio Rápido

### Ejecutar el backend:

```powershell
.\setup-and-run.ps1
```

Este script verifica todo automáticamente y arranca el servidor.

### Requisitos mínimos:
- ✅ Java 17+
- ✅ MongoDB (local, Docker, o Atlas)

## 📱 URLs para Android

- **Emulador**: `http://10.0.2.2:8080/api`
- **Dispositivo físico**: `http://TU_IP_LOCAL:8080/api`

## 🌐 Endpoints Principales

```
GET    /api/cycles                    # Listar ciclos
POST   /api/cycles                    # Nuevo periodo
GET    /api/cycles/next-period        # Predecir próximo periodo
GET    /api/cycles/ovulation          # Predecir ovulación
GET    /api/cycles/fertile-window     # Ventana fértil
GET    /api/prefs                     # Ver preferencias
PUT    /api/prefs                     # Actualizar preferencias
POST   /api/ovulation-observed        # Registrar ovulación observada
```

## 📄 Ejemplos de Datos

### Registrar periodo:
```json
POST /api/cycles
{
  "date": "2025-11-29"
}
```

### Actualizar preferencias:
```json
PUT /api/prefs
{
  "lastPeriodStart": "2025-11-29",
  "avgCycleLength": 28,
  "lutealLength": 14,
  "mensesLength": 5
}
```

---

Para más detalles, consulta: `INSTRUCCIONES_ANDROID.md`
