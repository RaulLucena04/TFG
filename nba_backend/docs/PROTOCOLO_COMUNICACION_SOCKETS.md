# Protocolo de comunicación cliente–servidor (NBA Predictor)

**Versión:** 1.0  
**Transporte:** TCP (puerto configurable, por defecto `9090`)  
**Codificación de mensajes:** ver sección *Capa de serialización (tramas)*.

Este documento describe el **protocolo de aplicación** (qué envía el cliente y qué responde el servidor).  
La implementación del router de acciones está en el backend: `com.tfg.nbabackend.socket.SocketDispatcher`.

---

## 1. Capa de serialización (tramas)

Antes de hablar de JSON, cada mensaje (petición o respuesta) viaja como **una trama**:

| Campo | Tipo | Descripción |
|--------|------|-------------|
| Longitud | 4 bytes, **big-endian** `int` | Número de bytes del siguiente bloque (UTF-8) |
| Cuerpo | `longitud` bytes | Texto UTF-8, en la práctica un único documento JSON |

- **Cliente → servidor:** una trama = un JSON de petición (`SocketRequest`).
- **Servidor → cliente:** una trama = un JSON de respuesta (`SocketResponse`).

En el código Java esta capa se llama `SocketFrameSerializer` (no confundir con el protocolo de negocio).

---

## 2. Modelo general: petición y respuesta

### 2.1 Petición (`SocketRequest`)

```json
{
  "requestId": "<string opcional pero recomendado>",
  "action": "<identificador de operación>",
  "payload": { }
}
```

- `action`: identificador en forma `dominio.operación` (ej. `team.list`).
- `payload`: objeto JSON; puede ser `{}` si no hay parámetros.

### 2.2 Respuesta (`SocketResponse`)

**Éxito:**

```json
{
  "requestId": "<mismo id si se envió>",
  "ok": true,
  "data": <objeto o array según la acción>,
  "error": null
}
```

**Error:**

```json
{
  "requestId": "<id o null>",
  "ok": false,
  "data": null,
  "error": "<mensaje legible>"
}
```

### 2.3 Semántica

- **Request/response:** por cada petición hay **exactamente una** respuesta en la misma conexión TCP.
- **Sin push:** el servidor no envía eventos unilaterales; el cliente debe volver a pedir datos si necesita refrescar (p. ej. puntos tras `match.finalize`).

---

## 3. Acciones soportadas (`action`)

Todas las respuestas exitosas van en `data` salvo indicación contraria.

### 3.1 Usuarios

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `user.register` | Objeto `Usuario` (campos de registro) | `Usuario` creado |
| `user.login` | `{ "username": "...", "password": "..." }` | `Usuario` |
| `user.list` | `{}` | Lista de `Usuario` (ordenados por puntos desc.) |
| `user.get` | `{ "id": <long> }` | `Usuario` |
| `user.update` | Objeto `Usuario` | `Usuario` actualizado |
| `user.changePassword` | `{ "id": <long>, "password": "..." }` | `{ "message": "Contraseña actualizada" }` |

### 3.2 Partidos

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `match.list` | `{}` | Lista de `Partido` |
| `match.create` | Objeto `Partido` (creación) | `Partido` guardado |
| `match.finalize` | `{ "id", "puntosLocal", "puntosVisitante" }` | `Partido` finalizado (resuelve apuestas en servidor) |
| `match.byTeam` | `{ "equipoId": <long> }` | Lista de `Partido` de ese equipo |

### 3.3 Equipos

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `team.list` | `{}` | Lista de `Equipo` |
| `team.get` | `{ "id": <long> }` | `Equipo` |
| `team.save` | Objeto `Equipo` | `Equipo` guardado |
| `team.stats` | `{ "equipoId": <long> }` | `EquipoEstadisticasDTO` |
| `team.withStats` | `{}` | Lista de `EquipoConEstadisticasDTO` |

### 3.4 Jugadores

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `player.list` | `{}` | Lista de `Jugador` |
| `player.byTeam` | `{ "equipoId": <long> }` | Lista de `Jugador` |
| `player.save` | Objeto `Jugador` | `Jugador` guardado |

### 3.5 Apuestas

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `bet.create` | Objeto `Apuesta` | `Apuesta` creada |
| `bet.byUser` | `{ "userId": <long> }` | Lista de `Apuesta` |

### 3.6 Tienda

| `action` | `payload` | `data` (éxito) |
|----------|-----------|----------------|
| `store.redeem` | Objeto `CanjearPuntosRequest` | `CanjearPuntosResponse` |

### 3.7 Acción desconocida

Si `action` no está soportada: `ok: false`, `error` con texto descriptivo.

---

## 4. Clientes de referencia

| Cliente | Ubicación | Notas |
|---------|-----------|--------|
| Android | `SocketApi.kt` + `SocketFrameSerializer.kt` | Una conexión TCP por llamada suspendida típica |
| JavaFX (`nba-client`) | `SocketApiClient.java` | Mismo formato de trama y JSON |
| Backend | `SocketServerRunner` + `SocketClientHandler` + `SocketDispatcher` | Acepta conexiones y atiende el bucle en `SocketClientHandler` |

---

## 5. Configuración típica

- **Emulador Android → PC anfitrión:** host `10.0.2.2`, puerto `9090` (o el configurado).
- **Dispositivo físico:** IP LAN del PC donde corre el backend, mismo Wi-Fi; firewall abierto para TCP del puerto del socket.

---

## Cómo generar un PDF a partir de este documento

1. Abre `PROTOCOLO_COMUNICACION_SOCKETS.html` en el navegador y usa **Imprimir → Guardar como PDF**.  
2. O convierte el `.md` con Pandoc:  
   `pandoc PROTOCOLO_COMUNICACION_SOCKETS.md -o PROTOCOLO_COMUNICACION_SOCKETS.pdf`
