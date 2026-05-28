# Sockets en Android y en el servidor

Este documento explica **cómo se conectan** la app Android y el backend Java, sin entrar en el detalle de cada operación (`user.login`, etc.). Para eso ver `PROTOCOLO_COMUNICACION_SOCKETS.md`.

---

## 1. Qué tipo de “socket” usamos

- **TCP** (conexión punto a punto), **no** WebSocket ni Socket.IO.
- Puerto por defecto: **9090** (configurable en backend y en la app).
- Los datos viajan como **JSON** dentro de una **trama** (4 bytes de longitud + texto UTF-8). En código: `SocketFrameSerializer` (Android y servidor).

La app **no** abre MySQL. Solo habla con el proceso Java del backend; ese proceso es quien usa la base de datos.

---

## 2. Lado servidor: escuchar y atender clientes

### 2.1 Arranque

1. Al iniciar Spring Boot, `SocketServerRunner` crea un `ServerSocket` en `0.0.0.0:9090` (escucha en todas las interfaces).
2. Un hilo (`socket-accept-loop`) hace `accept()` **en bucle**: cada vez que un cliente conecta, obtiene un `Socket` nuevo.

### 2.2 Por cada cliente conectado

1. El servidor mete en un pool la tarea `SocketClientHandler` (un hilo por conexión).
2. `SocketClientHandler` abre los streams de entrada/salida y entra en un **bucle**:
   - Lee una trama → JSON de petición.
   - Pasa la petición a `SocketDispatcher` (según `action`).
   - Escribe una trama con la respuesta JSON.
3. Si el cliente cierra la conexión, el bucle termina y el hilo acaba.

**Responsabilidades:**

| Clase | Qué hace |
|--------|-----------|
| `SocketServerRunner` | Solo `accept()`, delegar al pool y cerrar el servidor al apagar la app |
| `SocketClientHandler` | Atender **una** conexión TCP (varias peticiones seguidas en el mismo socket) |
| `SocketFrameSerializer` | Leer/escribir bytes de la trama |
| `SocketDispatcher` | Interpretar `action` y llamar a servicios/BD |

### 2.3 Esquema servidor

```
[Cliente Android]                    [Servidor Java]
      |                                    |
      |-------- TCP connect -------------->|
      |                                    | accept() → SocketClientHandler
      |-------- trama (petición JSON) ---->|
      |                                    | dispatcher → BD
      |<------- trama (respuesta JSON) ----|
      |-------- (otra petición opcional) ->|  mismo socket, mismo bucle
      |<------- respuesta -----------------|
      |-------- cierra conexión ---------->|  fin del handler
```

---

## 3. Lado Android: conectar y pedir datos

### 3.1 Quién inicia la conexión

**Siempre el móvil/emulador.** El servidor solo espera en `accept()`; no llama al teléfono.

### 3.2 Dónde está en el código

| Pieza | Archivo | Función |
|--------|---------|---------|
| Configuración IP/puerto | `ServerConfig.kt` | Host y puerto guardados (pantalla login: “Configurar servidor”) |
| Cliente de red | `SocketApi.kt` | Métodos `login`, `getPartidos`, etc. |
| Tramas | `SocketFrameSerializer.kt` | `writeFrame` / `readFrame` |
| Pantallas | `LoginScreen.kt`, `DashboardFragment.kt`, … | Llaman a `SocketApi` en corrutinas |

### 3.3 Una petición típica (ej. login)

1. La UI llama `SocketApi.login(usuario, contraseña)` dentro de `Dispatchers.IO` (hilo de red).
2. `SocketApi` monta el JSON: `requestId`, `action` = `"user.login"`, `payload` con usuario y contraseña.
3. Lee **host** y **puerto** de `ServerConfig` (por defecto en emulador: `10.0.2.2` y `9090`).
4. `Socket(host, port).use { ... }`:
   - **Conecta** al PC donde corre el backend.
   - `SocketFrameSerializer.writeFrame` → envía la petición.
   - `SocketFrameSerializer.readFrame` → lee la respuesta.
   - Al salir del `use`, **cierra** la conexión.
5. Si `ok` es true, convierte `data` a `User` y la UI la guarda en `Session`.

Casi todas las operaciones siguen el mismo patrón en `SocketApi.call(...)`: **una conexión nueva por llamada**.

### 3.4 Esquema Android (una llamada)

```
[Pantalla]  →  SocketApi.login()  →  Socket("10.0.2.2", 9090)
                                      writeFrame(petición)
                                      readFrame(respuesta)
                                      cerrar socket
                ←  User  ←  Gson
```

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