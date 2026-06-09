# NBA Predictor - Aplicación Android

Aplicación móvil Android desarrollada en Kotlin para el sistema de predicciones de partidos de la NBA.

## Características

- ✅ Autenticación (Login/Registro)
- ✅ Dashboard con estadísticas del usuario
- ✅ Visualización de partidos
- ✅ Sistema de apuestas con cuotas
- ✅ Rankings de usuarios
- ✅ Perfil de usuario
- ✅ Panel de administración (solo para admins)

## Tecnologías

- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Interfaz declarativa (flujo principal)
- **Gson** - Serialización JSON en el protocolo socket
- **Socket TCP** - Cliente `SocketApi` contra el backend (tramas JSON)
- **Coroutines** - Programación asíncrona
- **Material Design 3** - Componentes de UI
- **Navigation Compose** - Navegación entre pantallas

## Estructura del Proyecto

```
app/src/main/java/com/tfg/nbapredictor/
├── model/              # Modelos de datos (User, Partido, Apuesta, etc.)
├── network/            # Cliente socket (SocketApi), serialización de tramas
├── ui/
│   ├── auth/           # Login y registro (actividades)
│   ├── compose/        # Shell Compose, navegación y pantallas
│   ├── main/           # Actividad de arranque / redirección
│   ├── dashboard/      # Fragmentos legacy (conviven con Compose)
│   ├── matches/
│   ├── bets/
│   ├── rankings/
│   ├── profile/
│   └── admin/
└── util/               # Utilidades (Session, ServerConfig, etc.)
```

## Configuración

### 1. Host y puerto del backend (TCP)

El cliente Android habla con Spring Boot por **socket TCP** (JSON en tramas), **no** por HTTP/REST en el puerto 8080.

1. **Primera vez**: Al abrir la aplicación, configura **host:puerto** (ej. `10.0.2.2:9090`).
2. **Cambiar configuración**: Desde el login, botón **Configurar servidor**.
3. **Valores típicos**:
   - **Emulador**: `10.0.2.2:9090` (el emulador ve así el `localhost` de tu PC).
   - **Dispositivo físico**: `IP_LAN_DEL_PC:9090` (puerto definido en `nba_backend` → `socket.port`, por defecto 9090).

La configuración se guarda en `SharedPreferences` (`ServerConfig`).

### 2. Permisos

Los permisos de Internet ya están configurados en el `AndroidManifest.xml`.

### 3. Dependencias

Las dependencias están configuradas en `app/build.gradle`. Ejecuta:

```bash
./gradlew build
```

## Compilación y Ejecución

### Desde Android Studio

1. Abre el proyecto en Android Studio
2. Sincroniza el proyecto (Sync Project)
3. Conecta un dispositivo o inicia un emulador
4. Ejecuta la aplicación (Run 'app')

### APK con script (carpeta sencilla `Android/apk/`)

Desde la carpeta `Android`:

| Acción | Windows | Linux / Mac |
|--------|---------|-------------|
| Release (firma release si está configurada) | `build_release_apk.bat` | `./build_release_apk.sh` |
| Debug | `build_release_apk.bat debug` | `./build_release_apk.sh debug` |

El script hace `gradlew clean`, compila, **borra** los `.apk` viejos en `Android/apk/` y copia el resultado como `NBA-Predictor-release.apk` o `NBA-Predictor-debug.apk`.  
La salida interna de Gradle en Windows usa `%LOCALAPPDATA%\TFG-APK\build` (ruta corta, fuera de Documents).

## Requisitos

- Android Studio Hedgehog o superior
- Android SDK 24 (Android 7.0) o superior
- JDK 17
- Backend Spring Boot en ejecución con el **servidor TCP** activo (por defecto `0.0.0.0:9090` en el PC donde corre el `.jar`)

## Notas

- La app permite configurar host y puerto del backend desde la interfaz
- El valor por defecto en código es `10.0.2.2:9090` (emulador → PC anfitrión)
- Para dispositivos físicos, usa la IP real del servidor en la red local
- Tras cambiar host o puerto, la nueva configuración se aplica en la **siguiente** conexión (p. ej. al volver a iniciar sesión)
- El backend **no** expone una API HTTP de negocio para la app; no aplica CORS en ese canal (solo TCP + JSON)

## Funcionalidades Implementadas

### ✅ Completado (resumen)
- Flujo Compose principal (`ComposeMainActivity`, pantallas en `ui.compose.screens`)
- Autenticación, partidos, apuestas, rankings, perfil, tienda y panel admin (según pantallas y ViewModels actuales)
- Cliente `SocketApi` contra el backend TCP

### 🚧 Posibles extensiones
- Pulido de UX, pruebas automatizadas amplias y caché offline
- Notificaciones push y métricas

## Contribución

Este proyecto es parte de un TFG. Para más información, consulta la documentación del proyecto principal.
