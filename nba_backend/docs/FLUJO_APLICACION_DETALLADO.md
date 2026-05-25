# Flujo detallado de la aplicación NBA Predictor

Este documento describe **qué piezas intervienen** y **en qué orden** se ejecutan cuando un usuario usa la app, para que puedas seguir el recorrido de los datos en el código.

---

## 1. Visión general (capas)

```
┌─────────────────────────────────────────────────────────────────────────┐
│  CLIENTE (Android o JavaFX nba-client)                                   │
│  · Pantallas / controladores (UI)                                        │
│  · Llamadas a SocketApi / SocketApiClient                                │
└───────────────────────────────┬─────────────────────────────────────────┘
                                │ TCP + tramas JSON (puerto 9090 por defecto)
                                ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  BACKEND (Spring Boot, módulo nba_backend)                                │
│  · SocketServerRunner → acepta conexiones                                │
│  · SocketClientHandler → bucle por cliente                               │
│  · SocketDispatcher → elige acción (user.login, match.list, …)           │
│  · *Service → reglas de negocio                                           │
│  · *Repository (Spring Data JPA) → SQL                                   │
└───────────────────────────────┬─────────────────────────────────────────┘
                                │ JDBC
                                ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  MySQL (base nba_app)                                                     │
└─────────────────────────────────────────────────────────────────────────┘
```

**Importante:** los clientes **no** hablan con MySQL directamente. Solo el backend abre la conexión JDBC.

---

## 2. Arranque del backend

1. Se ejecuta la clase principal `com.tfg.nbabackend.NbaBackendApplication` (Maven: `spring-boot:run`).
2. Spring Boot levanta el **contexto** (beans: servicios, repositorios, `ObjectMapper`, etc.).
3. `spring.jpa.hibernate.ddl-auto=update` hace que Hibernate **cree o actualice tablas** según las entidades (`Usuario`, `Partido`, …).
4. `SocketServerRunner` implementa `CommandLineRunner`: al arrancar abre un `ServerSocket` en `socket.bindAddress` / `socket.port` y arranca el hilo `socket-accept-loop` que hace `accept()` en bucle.
5. Cada `Socket` aceptado se encola en un pool: `new SocketClientHandler(socket, mapper, dispatcher)`.

A partir de ahí, el servidor **solo reacciona** cuando un cliente conecta y envía tramas.

---

## 3. Recorrido genérico de una petición por socket

### 3.1 Del cliente al backend

| Paso | Dónde | Qué ocurre |
|------|--------|-------------|
| 1 | **UI** (ej. `LoginScreen.kt`, `DashboardFragment.kt`, controladores JavaFX) | El usuario pulsa un botón o se carga una pantalla; se lanza una corrutina o un hilo que llama al cliente de red. |
| 2 | **Configuración de red** | **Android:** `ServerConfig` (host/puerto en `SharedPreferences`, por defecto `10.0.2.2:9090` en emulador). **JavaFX:** `util.Config` equivalente. |
| 3 | **Cliente socket** | **Android:** `SocketApi.call()` en `Dispatchers.IO` construye JSON `{ requestId, action, payload }`, abre `Socket(host, port)`, escribe trama con `SocketFrameSerializer.writeFrame`. **JavaFX:** `SocketApiClient.requestRaw()` hace lo mismo con `writeFrame` interno. |
| 4 | **Red TCP** | Los bytes viajan al proceso del backend. |
| 5 | **`SocketClientHandler.run()`** | `SocketFrameSerializer.readFrame(in)` obtiene un string JSON → `ObjectMapper` lo convierte en `SocketRequest`. |
| 6 | **`SocketDispatcher.handle(req)`** | `switch` sobre `req.action` (ej. `user.login`). Parsea `payload` y llama al **servicio** correspondiente (`UsuarioService`, `PartidoService`, …). |
| 7 | **Servicio** | Lógica de negocio (validar contraseña, descontar puntos al apostar, finalizar partido y resolver apuestas, etc.). |
| 8 | **Repositorio JPA** | Consultas/updates en MySQL (`UsuarioRepository`, `PartidoRepository`, …). |
| 9 | **Respuesta** | El servicio devuelve un objeto (o lista). `SocketDispatcher` lo envuelve en `SocketResponse.ok(requestId, data)`. |
| 10 | **Vuelta al cliente** | `ObjectMapper` serializa `SocketResponse` a JSON → `SocketFrameSerializer.writeFrame` → el cliente lee una trama y parsea `ok` / `data` / `error`. |
| 11 | **UI** | Gson/Jackson convierte `data` al modelo de pantalla (`User`, `Partido`, …) y Compose/XML actualiza la vista o se guarda en `Session`. |

Cada llamada típica de **Android** abre **una conexión TCP nueva**, envía **una** petición, lee **una** respuesta y cierra (`Socket(...).use { }`). El **JavaFX** client hace lo mismo en `try (Socket ...)`.

---

## 4. Ejemplo: inicio de sesión (Android)

1. **`LoginScreen.kt`**: el usuario introduce usuario/contraseña y pulsa iniciar sesión → `scope.launch { SocketApi.login(...) }`.
2. **`SocketApi.login`**: delega en `call("user.login", mapOf("username", "password"), User::class.java)`.
3. **`SocketApi.call`**: genera `requestId`, monta el `JsonObject`, lee host/puerto de `ServerConfig.getServerHost()` / `getServerPort()`, conecta y envía la trama.
4. **`SocketDispatcher`**: rama `user.login` → `handleLogin` → `UsuarioService.login(username, password)`.
5. **`UsuarioService`**: busca en BD, comprueba BCrypt o contraseña en texto plano (migración), devuelve `Usuario` o falla.
6. **Respuesta**: `SocketResponse.ok` con el usuario → el cliente deserializa a `User`.
7. **`LoginScreen`**: `Session.setCurrentUser(user)` y navega a la actividad principal.

Si `ok` es false, `SocketApi` lanza excepción y la UI muestra el mensaje de error.

---

## 5. Ejemplo: listar partidos (cualquier cliente)

1. UI (p. ej. `MatchesScreen.kt` o `PartidoService.listarPartidos()` en JavaFX) pide datos.
2. Petición `action = "match.list"`, `payload = {}`.
3. **`SocketDispatcher`**: `partidoService.listarPartidos()`.
4. **`PartidoService`** → **`PartidoRepository.findAll()`** → Hibernate ejecuta `SELECT` en tabla `partido` (con joins a equipos si están mapeados).
5. Lista de entidades `Partido` serializada en JSON dentro de `data`.

El cliente pinta la lista; **no hay caché global en servidor**: cada listado es una petición nueva.

---

## 6. Ejemplo: crear una apuesta

1. UI recoge partido, predicción y puntos → construye modelo `Apuesta` (con `usuario.id` y `partido.id`).
2. **`bet.create`** con el objeto en `payload`.
3. **`ApuestaService.crearApuesta`**: carga usuario, comprueba saldo, calcula cuota si hace falta, **resta puntos** al usuario, guarda apuesta con resultado `PENDIENTE`.
4. Varias escrituras en BD en la misma petición (usuario + apuesta).
5. Respuesta: apuesta creada al cliente.

---

## 7. Ejemplo: administrador finaliza un partido (JavaFX)

1. **`MatchFormController`** / panel admin → `PartidoService.finalizarPartido(id, puntosLocal, puntosVisitante)`.
2. **`SocketApiClient.request("match.finalize", Map.of(...), Partido.class)`**.
3. **`PartidoService.finalizarPartido`** (backend): actualiza marcador y estado, guarda partido, llama **`ApuestaService.resolverApuestas(partido)`**.
4. **`resolverApuestas`**: para cada apuesta pendiente del partido compara predicción con resultado, marca GANADA/PERDIDA y **suma puntos** al usuario si ganó.
5. Respuesta: `Partido` finalizado.

Los clientes que quieran ver **ranking o saldo actualizado** deben volver a llamar a `user.get` / `user.list` (el protocolo socket es **request/response**, sin notificaciones push).

---

## 8. Dónde mirar en el código (mapa rápido)

| Tema | Ubicación principal |
|------|----------------------|
| Aceptar conexiones TCP | `nba_backend/.../socket/SocketServerRunner.java` |
| Bucle por cliente | `nba_backend/.../socket/SocketClientHandler.java` |
| Tramas length-prefixed | `nba_backend/.../socket/SocketFrameSerializer.java` |
| Tabla de acciones | `nba_backend/.../socket/SocketDispatcher.java` |
| Reglas de negocio | `nba_backend/.../service/*.java` |
| Entidades / BD | `nba_backend/.../model/*.java`, `.../repository/*.java` |
| Cliente Android | `Android/.../network/SocketApi.kt`, `SocketFrameSerializer.kt`, `ServerConfig.kt` |
| Cliente escritorio | `nba-client/.../service/SocketApiClient.java`, `.../service/*Service.java` |

---

## 9. Relación con otros documentos

- **Contrato de mensajes (acciones, payloads):** `docs/PROTOCOLO_COMUNICACION_SOCKETS.md` (y `.html` para imprimir a PDF).

Si quieres profundizar en **una sola pantalla**, abre la UI en Android o el `.fxml`/controller en JavaFX y sigue la referencia a `SocketApi` o `SocketApiClient` hasta `SocketDispatcher` en el backend.
