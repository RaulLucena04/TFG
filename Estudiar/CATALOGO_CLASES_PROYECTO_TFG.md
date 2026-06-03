# Catálogo de clases del proyecto TFG (NBA Predictor)

Este documento describe la **funcionalidad** de cada clase del código fuente del proyecto, organizada por módulo: **servidor** (`nba_backend`), **cliente de escritorio** (`nba-client`) y **Android** (`Android/app/src/main/java`). No incluye archivos generados por Gradle ni stubs de KAPT.

**Versión HTML (impresión / PDF):** [`CATALOGO_CLASES_PROYECTO_TFG.html`](CATALOGO_CLASES_PROYECTO_TFG.html) — abrir en el navegador y usar *Imprimir → Guardar como PDF*.

**Convención de rutas:** las rutas son relativas a la raíz del repositorio `TFG`.

---

## 1. Visión general de la arquitectura

| Módulo | Tecnología | Rol |
|--------|------------|-----|
| `nba_backend` | Spring Boot, JPA, MySQL | Persistencia, reglas de negocio, servidor TCP en el puerto 9090 |
| `nba-client` | JavaFX | Aplicación de escritorio para usuario y administrador; habla con el backend por **TCP + JSON** |
| `Android` | Kotlin, Jetpack (Fragments, Compose), Gson | App móvil; misma API TCP que el cliente JavaFX |

Los **modelos** (usuario, partido, apuesta, etc.) se repiten en los tres módulos como clases independientes adaptadas a cada plataforma (Java vs Kotlin, anotaciones JPA solo en el servidor).

---

## 2. Servidor — `nba_backend`

**Paquete base:** `com.tfg.nbabackend`

### 2.1 Punto de entrada y configuración

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`NbaBackendApplication`** | `nba_backend/src/main/java/com/tfg/nbabackend/NbaBackendApplication.java` | Clase principal de Spring Boot (`@SpringBootApplication`). Arranca el contexto de la aplicación, escanea componentes y activa JPA. Junto con el arranque normal de Spring, el componente `SocketServerRunner` abre el servidor TCP. |
| **`JacksonConfig`** | `.../config/JacksonConfig.java` | Configuración de **Jackson** (`ObjectMapper`) para serialización JSON coherente en el backend (incluye soporte de fechas/hora Java 8 si está registrado en el bean). Lo usa el protocolo socket al convertir peticiones y respuestas. |

### 2.2 Capa socket (protocolo TCP)

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`SocketServerRunner`** | `.../socket/SocketServerRunner.java` | Implementa `CommandLineRunner` y `DisposableBean`. Tras el arranque de Spring, crea un `ServerSocket` en `0.0.0.0:9090` (configurable) y un hilo dedicado que hace `accept()` en bucle. Cada conexión entrante se delega al pool de hilos ejecutando `SocketClientHandler`. Al apagar la aplicación, cierra el `ServerSocket` y el pool. |
| **`SocketClientHandler`** | `.../socket/SocketClientHandler.java` | `Runnable` que atiende **una** conexión TCP. Bucle: lee una trama con `SocketFrameSerializer`, parsea JSON a `SocketRequest`, invoca `SocketDispatcher.dispatch`, escribe la respuesta como trama. Si el cliente cierra o hay error de lectura, termina el bucle. |
| **`SocketFrameSerializer`** | `.../socket/SocketFrameSerializer.java` | Capa de transporte: escribe y lee tramas **int32 big-endian (longitud en bytes)** + cuerpo **UTF-8**. No interpreta el negocio; solo bytes. |
| **`SocketDispatcher`** | `.../socket/SocketDispatcher.java` | **Router** del protocolo de aplicación. Según el campo `action` del JSON (`user.login`, `match.list`, `bet.create`, etc.) delega en el servicio correspondiente, construye `SocketResponse` con `ok`/`data` o `ok`/`error`. Convierte `payload` con Jackson a DTOs o entidades según la acción. |
| **`SocketRequest`** | `.../socket/SocketRequest.java` | DTO de entrada del protocolo: `requestId`, `action`, `payload` (nodo JSON genérico). |
| **`SocketResponse`** | `.../socket/SocketResponse.java` | DTO de salida: `requestId`, `ok`, `data` (opcional), `error` (opcional). |

### 2.3 Modelo de dominio y persistencia (JPA)

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`Usuario`** | `.../model/Usuario.java` | Entidad JPA del usuario: credenciales, rol, puntos, saldo, etc. Mapea a la tabla de usuarios en MySQL. |
| **`Partido`** | `.../model/Partido.java` | Entidad JPA del partido: equipos locales/visitantes, fechas, estado (`EstadoPartido`), marcador, etc. |
| **`Apuesta`** | `.../model/Apuesta.java` | Entidad JPA de la apuesta: usuario, partido, pronóstico, importe, resultado (`ResultadoApuesta`), estado. |
| **`Equipo`** | `.../model/Equipo.java` | Entidad JPA del equipo NBA (nombre, ciudad, etc.). |
| **`Jugador`** | `.../model/Jugador.java` | Entidad JPA del jugador asociado a un equipo y estadísticas agregadas en BD. |
| **`Rol`** | `.../model/Rol.java` | Enum o tipo de rol de usuario (por ejemplo `USER`, `ADMIN`) usado en `Usuario`. |
| **`EstadoPartido`** | `.../enums/EstadoPartido.java` | Enum del ciclo de vida del partido (programado, en curso, finalizado, etc.). |
| **`ResultadoApuesta`** | `.../enums/ResultadoApuesta.java` | Enum del resultado de la apuesta respecto al partido (ganada, perdida, pendiente, etc.). |
| **`UsuarioRepository`** | `.../repository/UsuarioRepository.java` | Interfaz Spring Data JPA para CRUD y consultas sobre `Usuario`. |
| **`PartidoRepository`** | `.../repository/PartidoRepository.java` | Repositorio JPA de `Partido`. |
| **`ApuestaRepository`** | `.../repository/ApuestaRepository.java` | Repositorio JPA de `Apuesta`; puede incluir consultas por usuario o partido. |
| **`EquipoRepository`** | `.../repository/EquipoRepository.java` | Repositorio JPA de `Equipo`. |
| **`JugadorRepository`** | `.../repository/JugadorRepository.java` | Repositorio JPA de `Jugador` (por ejemplo por equipo). |

### 2.4 Servicios de negocio

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`UsuarioService`** | `.../service/UsuarioService.java` | Lógica de usuarios: registro, login (validación de contraseña/hash), listado, actualización, consulta por id. Invocado desde `SocketDispatcher` en acciones `user.*`. |
| **`PartidoService`** | `.../service/PartidoService.java` | Gestión de partidos: crear, listar, obtener, actualizar marcador/estado, **finalizar partido** (transaccional). Al finalizar puede disparar la resolución de apuestas vía `ApuestaService`. |
| **`ApuestaService`** | `.../service/ApuestaService.java` | Crear apuestas, listar por usuario, historial, **resolver apuestas** cuando un partido termina (comparar pronóstico con resultado, actualizar puntos/saldo del usuario). |
| **`EquipoService`** | `.../service/EquipoService.java` | Listar equipos, obtener detalle, operaciones de catálogo de equipos. |
| **`JugadorService`** | `.../service/JugadorService.java` | Listado y consulta de jugadores por equipo u otros criterios. |
| **`EquipoEstadisticasService`** | `.../service/EquipoEstadisticasService.java` | Cálculo o agregación de estadísticas de equipos para rankings o pantallas de estadísticas. |
| **`TiendaService`** | `.../service/TiendaService.java` | Lógica de **canje de puntos** por recompensas o integración con flujo de “tienda” expuesto por socket. |
| **`PayPalService`** | `.../service/PayPalService.java` | Cliente HTTP (`HttpClient`) hacia la API de **PayPal** (OAuth2, sandbox/producción). Usado cuando el negocio requiere validar o crear pagos reales o de prueba según configuración en `application.properties`. |

### 2.5 DTOs y utilidades del servidor

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`EquipoEstadisticasDTO`** | `.../dto/EquipoEstadisticasDTO.java` | Objeto de transferencia con estadísticas de un equipo para enviar al cliente sin exponer la entidad JPA completa. |
| **`EquipoConEstadisticasDTO`** | `.../dto/EquipoConEstadisticasDTO.java` | DTO que combina datos de equipo con bloque de estadísticas para listados enriquecidos. |
| **`CanjearPuntosRequest`** | `.../dto/CanjearPuntosRequest.java` | Cuerpo de petición para operaciones de canje de puntos (campos acordados con el cliente). |
| **`CanjearPuntosResponse`** | `.../dto/CanjearPuntosResponse.java` | Respuesta estructurada tras un canje (éxito, mensaje, saldos actualizados, etc.). |
| **`ApiError`** | `.../exception/ApiError.java` | Representación de error de API (mensaje) para respuestas uniformes en capas que no usan solo el socket. |
| **`PasswordHashGenerator`** | `.../util/PasswordHashGenerator.java` | Utilidad de línea de comandos o helper para generar hashes de contraseña (BCrypt u otro) usados al poblar usuarios de prueba o administración fuera de la app. |

### 2.6 Pruebas

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`NbaBackendApplicationTests`** | `nba_backend/src/test/java/com/tfg/nbabackend/NbaBackendApplicationTests.java` | Test de integración ligero de Spring Boot: verifica que el contexto de la aplicación arranca correctamente (`@SpringBootTest`). |

---

## 3. Cliente de escritorio — `nba-client`

**Paquetes típicos:** `start`, `controller.*`, `service`, `model`, `util`, `session`

### 3.1 Arranque

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`Main`** | `nba-client/src/main/java/start/Main.java` | Punto de entrada JavaFX: inicializa el toolkit, carga la primera vista (login o layout principal según diseño) y arranca el ciclo de vida de la aplicación de escritorio. |

### 3.2 Sesión y configuración

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`Session`** | `nba-client/src/main/java/session/Session.java` | Singleton o contenedor del **usuario logueado** y datos de sesión (token lógico, usuario actual) compartidos entre controladores tras el login. |
| **`Config`** | `nba-client/src/main/java/util/Config.java` | Lectura de **host y puerto** del servidor socket (propiedades o valores por defecto) usados por `SocketApiClient`. |

### 3.3 Comunicación con el backend

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`SocketApiClient`** | `nba-client/src/main/java/service/SocketApiClient.java` | Cliente TCP: abre `Socket`, envía JSON con `requestId`, `action`, `payload` en trama length-prefixed, lee la respuesta, interpreta `ok`/`error`/`data` con Jackson. Es el equivalente Java del `SocketApi` de Android. |

### 3.4 Servicios del cliente (fachada sobre el socket)

Estas clases agrupan llamadas a `SocketApiClient` por dominio para que los controladores no construyan JSON a mano.

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`UsuarioService`** | `nba-client/src/main/java/service/UsuarioService.java` | Login, registro, perfil, listado de usuarios (admin), actualización de usuario vía socket. |
| **`PartidoService`** | `.../service/PartidoService.java` | Listado, creación, detalle, finalización de partidos (admin). |
| **`ApuestaService`** | `.../service/ApuestaService.java` | Crear apuesta, listar activas, historial, detalle. |
| **`EquipoApiService`** | `.../service/EquipoApiService.java` | Equipos y posiblemente estadísticas de equipos desde el backend. |
| **`JugadorService`** | `.../service/JugadorService.java` | Jugadores por equipo o listados globales. |
| **`TiendaService`** | `.../service/TiendaService.java` | Operaciones de tienda / canje de puntos contra el backend. |

### 3.5 Modelos del cliente (POJO, sin JPA)

Reflejan la forma de los JSON que intercambian cliente y servidor; no se persisten en el cliente.

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`User`** | `nba-client/src/main/java/model/User.java` | Modelo de usuario para vistas y envío en peticiones. |
| **`Partido`** | `.../model/Partido.java` | Modelo de partido para tablas y formularios. |
| **`Apuesta`** | `.../model/Apuesta.java` | Modelo de apuesta para listados y detalle. |
| **`Equipo`** | `.../model/Equipo.java` | Modelo de equipo. |
| **`Jugador`** | `.../model/Jugador.java` | Modelo de jugador. |
| **`EquipoEstadisticas`** | `.../model/EquipoEstadisticas.java` | Estadísticas agregadas de equipo para pantallas de ranking/estadísticas. |

### 3.6 Utilidades de UI

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`TableFormatters`** | `nba-client/src/main/java/util/TableFormatters.java` | Formateo de celdas JavaFX (fechas, decimales, enums) para `TableView` y controles similares. |

### 3.7 Controladores JavaFX (por área funcional)

Los controladores enlazan FXML/eventos con los servicios y actualizan la UI.

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`LoginController`** | `.../controller/auth/login/LoginController.java` | Pantalla de login: validación básica, llamada a `UsuarioService`, guardado en `Session`, navegación al dashboard. |
| **`RegisterController`** | `.../controller/auth/registro/RegisterController.java` | Registro de nuevo usuario. |
| **`ChangePasswordController`** | `.../controller/auth/password/ChangePasswordController.java` | Cambio de contraseña del usuario autenticado. |
| **`MainLayoutController`** | `.../controller/layout/MainLayoutController.java` | Layout principal (menú, barra, contenedor de vistas hijas). |
| **`DashboardController`** | `.../controller/dashboard/DashboardController.java` | Panel principal del usuario: resumen, accesos a partidos/apuestas. |
| **`MatchesController`** | `.../controller/partidos/MatchesController.java` | Listado de partidos disponibles para apostar o consultar. |
| **`MatchDetailController`** | `.../controller/partidos/MatchDetailController.java` | Detalle de un partido concreto. |
| **`CreateBetController`** | `.../controller/apuestas/CreateBetController.java` | Formulario de creación de apuesta. |
| **`BetsController`** | `.../controller/apuestas/BetsController.java` | Contenedor o pestaña de apuestas del usuario. |
| **`ActiveBetsController`** | `.../controller/apuestas/ActiveBetsController.java` | Listado de apuestas activas. |
| **`BetHistoryController`** | `.../controller/apuestas/BetHistoryController.java` | Historial de apuestas resueltas. |
| **`BetDetailController`** | `.../controller/apuestas/BetDetailController.java` | Detalle de una apuesta. |
| **`ProfileController`** | `.../controller/perfil/ProfileController.java` | Perfil del usuario (datos, puntos, edición limitada). |
| **`RankingsController`** | `.../controller/rankings/RankingsController.java` | Ranking de usuarios por puntos. |
| **`TiendaController`** | `.../controller/tienda/TiendaController.java` | Pantalla de tienda / canje. |
| **`StatisticsController`** | `.../controller/estadisticas/StatisticsController.java` | Navegación o contenedor de la sección de estadísticas. |
| **`TeamsStatsController`** | `.../controller/estadisticas/TeamsStatsController.java` | Estadísticas por equipos. |
| **`PlayersStatsController`** | `.../controller/estadisticas/PlayersStatsController.java` | Estadísticas por jugadores. |
| **`TeamDetailController`** | `.../controller/estadisticas/TeamDetailController.java` | Detalle estadístico de un equipo. |
| **`AdminDashboardController`** | `.../controller/admin/AdminDashboardController.java` | Panel de administración: accesos a gestión de partidos y usuarios. |
| **`MatchManagementController`** | `.../controller/admin/MatchManagementController.java` | Gestión del ciclo de vida de partidos (crear, editar, listar para admin). |
| **`MatchFormController`** | `.../controller/admin/MatchFormController.java` | Formulario alta/edición de partido. |
| **`UserManagementController`** | `.../controller/admin/UserManagementController.java` | Administración de usuarios (roles, bloqueo, puntos si aplica). |

---

## 4. Aplicación Android — `Android/app/src/main/java/com/tfg/nbapredictor`

### 4.1 Aplicación y contexto

| Clase / objeto | Ruta | Funcionalidad |
|----------------|------|---------------|
| **`NBAPredictorApplication`** | `.../NBAPredictorApplication.kt` | Subclase de `Application` para inicialización global (si está configurada en el `AndroidManifest`). |
| **`AppContext`** | `.../util/AppContext.kt` | Acceso al `Context` de aplicación de forma estática o centralizada donde hace falta fuera de `Activity` (por ejemplo para `ServerConfig` o inicializaciones). |

### 4.2 Configuración de red y sesión

| Clase / objeto | Ruta | Funcionalidad |
|----------------|------|---------------|
| **`ServerConfig`** | `.../util/ServerConfig.kt` | Singleton: guarda y lee **host** y **puerto** del backend en `SharedPreferences`; valores por defecto para emulador (`10.0.2.2:9090`); heurística `isProbablyEmulator()`; puede resetear el cliente Retrofit al cambiar la base URL si la app usa ambos canales. |
| **`Session`** | `.../util/Session.kt` | Estado de sesión del usuario logueado en memoria (objeto `User` o similar) compartido entre pantallas. |

### 4.3 Red: sockets (canal principal con el backend)

| Clase / objeto | Ruta | Funcionalidad |
|----------------|------|---------------|
| **`SocketApi`** | `.../network/SocketApi.kt` | Objeto singleton con funciones `suspend` que ejecutan en `Dispatchers.IO`: para cada operación abre `Socket`, serializa petición con **Gson**, usa `SocketFrameSerializer`, parsea `SocketResponse` y devuelve modelos Kotlin o lanza excepción si `ok == false`. Agrupa métodos (`login`, `getPartidos`, `createBet`, etc.) mapeados a `action` del backend. |
| **`SocketFrameSerializer`** | `.../network/SocketFrameSerializer.kt` | Igual que en el servidor: trama **4 bytes longitud + UTF-8**. |
| **`LocalDateTimeAdapter`** | `.../network/LocalDateTimeAdapter.kt` | `TypeAdapter` de Gson para serializar/deserializar `LocalDateTime` de forma compatible con el JSON del backend. |

### 4.4 Red: Retrofit (API REST declarada; uso según evolución del proyecto)

| Clase / interfaz | Ruta | Funcionalidad |
|------------------|------|---------------|
| **`ApiService`** | `.../network/ApiService.kt` | Interfaz **Retrofit** con endpoints REST (`usuarios`, `partidos`, etc.). Incluye `LoginRequest` y el data class **`ApiError`** para parsear cuerpos de error `{"error":"..."}`. Puede coexistir con `SocketApi` si alguna pantalla aún usa HTTP; la documentación del TFG debe aclarar qué flujo está activo en producción. |
| **`RetrofitClient`** | `.../network/RetrofitClient.kt` | Construye la instancia de Retrofit (base URL desde `ServerConfig`, OkHttp, conversores Gson). |

### 4.5 Modelos Kotlin

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`User`** | `.../model/User.kt` | Data class usuario (id, username, puntos, rol, etc.) alineada con el JSON del backend. |
| **`Partido`** | `.../model/Partido.kt` | Data class partido. |
| **`Apuesta`** | `.../model/Apuesta.kt` | Data class apuesta. |
| **`Equipo`** | `.../model/Equipo.kt` | Data class equipo. |
| **`Jugador`** | `.../model/Jugador.kt` | Data class jugador. |
| **`EquipoEstadisticas`** | `.../model/EquipoEstadisticas.kt` | Estadísticas de equipo para rankings. |

### 4.6 Actividades “clásicas” y flujo híbrido

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`LoginActivity`** | `.../ui/auth/LoginActivity.kt` | Actividad de login (puede lanzar Compose o fragments según diseño). |
| **`RegisterActivity`** | `.../ui/auth/RegisterActivity.kt` | Actividad de registro. |
| **`MainActivity`** | `.../ui/main/MainActivity.kt` | Actividad principal con navegación por **Fragments** y `BottomNavigation` (flujo “clásico” XML). |
| **`ComposeMainActivity`** | `.../ui/compose/ComposeMainActivity.kt` | Actividad host de **Jetpack Compose** que carga el árbol `NBAComposeApp`. |
| **`AdminActivity`** | `.../ui/admin/AdminActivity.kt` | Pantalla o contenedor de administración (gestión de partidos en móvil). |

### 4.7 Fragments (vista XML / ViewBinding)

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`DashboardFragment`** | `.../ui/dashboard/DashboardFragment.kt` | Inicio del usuario: partidos destacados, accesos. |
| **`MatchesFragment`** | `.../ui/matches/MatchesFragment.kt` | Listado de partidos. |
| **`MatchDetailFragment`** | `.../ui/matches/MatchDetailFragment.kt` | Detalle de partido. |
| **`BetsFragment`** | `.../ui/bets/BetsFragment.kt` | Listado de apuestas del usuario. |
| **`CreateBetDialogFragment`** | `.../ui/bets/CreateBetDialogFragment.kt` | Diálogo para crear apuesta (wrapper que puede invocar lógica Compose o vistas). |
| **`ProfileFragment`** | `.../ui/profile/ProfileFragment.kt` | Perfil y datos del usuario. |
| **`RankingsFragment`** | `.../ui/rankings/RankingsFragment.kt` | Fragment contenedor de ranking. |

### 4.8 Adaptadores de `RecyclerView`

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`MatchesAdapter`** | `.../ui/dashboard/MatchesAdapter.kt` | Adaptador de lista de partidos en dashboard. |
| **`BetsAdapter`** | `.../ui/bets/BetsAdapter.kt` | Adaptador de lista de apuestas. |
| **`RankingsAdapter`** | `.../ui/rankings/RankingsAdapter.kt` | Adaptador de filas de ranking. |
| **`AdminMatchesAdapter`** | `.../ui/admin/AdminMatchesAdapter.kt` | Lista de partidos para administrador (acciones de finalizar, etc.). |

### 4.9 Diálogos admin

| Clase | Ruta | Funcionalidad |
|--------|------|---------------|
| **`FinalizeMatchDialogFragment`** | `.../ui/admin/FinalizeMatchDialogFragment.kt` | Diálogo para introducir resultado final y llamar al backend para **finalizar** el partido (resolución de apuestas en servidor). |

### 4.10 Jetpack Compose — navegación y pantallas

| Clase / objeto | Ruta | Funcionalidad |
|----------------|------|---------------|
| **`NBAComposeApp`** | `.../ui/compose/NBAComposeApp.kt` | Raíz Compose: `NavHost`, tema y pantallas registradas. Incluye el `data class` privado **`BottomNavItem`** (ruta, etiqueta, icono) para los ítems de la barra inferior. |
| **`NavSharedState`** | `.../ui/compose/NavSharedState.kt` | Estado compartido entre pantallas Compose (usuario actual, flags de navegación, etc.). |
| **`DashboardViewModel`** | `.../ui/compose/viewmodel/DashboardViewModel.kt` | `ViewModel` del dashboard Compose: expone `StateFlow<DashboardState>`, carga partidos y datos vía corrutinas y `SocketApi`. |
| **`DashboardState`** | `.../ui/compose/viewmodel/DashboardViewModel.kt` | `data class` en el mismo archivo que `DashboardViewModel`: estado inmutable de la UI del dashboard (cargando, error, lista de partidos). |
| **`LoginScreen`** | `.../ui/compose/screens/LoginScreen.kt` | UI Compose de login y configuración de servidor. |
| **`RegisterScreen`** | `.../ui/compose/screens/RegisterScreen.kt` | UI Compose de registro. |
| **`DashboardScreen`** | `.../ui/compose/screens/DashboardScreen.kt` | UI Compose del panel principal. |
| **`MatchesScreen`** | `.../ui/compose/screens/MatchesScreen.kt` | Listado de partidos en Compose. |
| **`MatchDetailScreen`** | `.../ui/compose/screens/MatchDetailScreen.kt` | Detalle de partido en Compose. |
| **`BetsScreen`** | `.../ui/compose/screens/BetsScreen.kt` | Apuestas en Compose. |
| **`CreateBetDialog`** | `.../ui/compose/screens/CreateBetDialog.kt` | Diálogo Compose para nueva apuesta. |
| **`RankingsScreen`** | `.../ui/compose/screens/RankingsScreen.kt` | Ranking en Compose. |
| **`ProfileScreen`** | `.../ui/compose/screens/ProfileScreen.kt` | Perfil en Compose. |
| **`StoreScreen`** | `.../ui/compose/screens/StoreScreen.kt` | Tienda / canje en Compose. |
| **`AdminScreen`** | `.../ui/compose/screens/AdminScreen.kt` | Administración de partidos en Compose. |
| **`MatchItem`** | `.../ui/compose/components/MatchItem.kt` | Composable reutilizable que pinta una fila de partido. |

---

## 5. Cómo mantener este catálogo

- Al **añadir** una clase nueva, incorpórala en la sección del módulo correspondiente con: nombre, ruta relativa y una frase de responsabilidad.
- Al **refactorizar** (por ejemplo renombrar `SocketProtocol` a `SocketFrameSerializer`), actualiza el nombre aquí y en `nba_backend/docs/README.md` si aplica.
- Los **scripts SQL** (`create_empty_database.sql`, `populate_database.sql`) no son clases; documentan el esquema de BD aparte.

---

## 6. Referencias cruzadas

| Documento | Contenido |
|-----------|-----------|
| `nba_backend/docs/PROTOCOLO_COMUNICACION_SOCKETS.md` | Lista de `action` y payloads |
| `nba_backend/docs/SOCKETS_ANDROID_Y_SERVIDOR.md` | Conexión TCP y clases implicadas |
| `nba_backend/docs/FLUJO_APLICACION_DETALLADO.md` | Flujo de datos entre capas |

---

*Documento generado para el TFG — catálogo de clases del código fuente (servidor, cliente JavaFX, Android).*
