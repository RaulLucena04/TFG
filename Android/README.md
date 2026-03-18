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
- **Retrofit** - Cliente HTTP para llamadas a la API
- **Coroutines** - Programación asíncrona
- **Material Design** - Componentes de UI
- **ViewBinding** - Binding de vistas
- **Navigation Component** - Navegación entre pantallas

## Estructura del Proyecto

```
app/src/main/java/com/tfg/nbapredictor/
├── model/              # Modelos de datos (User, Partido, Apuesta, etc.)
├── network/            # Servicios de red (Retrofit, ApiService)
├── ui/                 # Actividades y Fragmentos
│   ├── auth/          # Login y Registro
│   ├── main/          # Actividad principal
│   ├── dashboard/     # Dashboard
│   ├── matches/       # Partidos
│   ├── bets/          # Apuestas
│   ├── rankings/      # Rankings
│   ├── profile/       # Perfil
│   └── admin/         # Administración
└── util/              # Utilidades (Session, etc.)
```

## Configuración

### 1. URL del Backend

La URL del servidor se configura desde la interfaz de usuario:

1. **Primera vez**: Al abrir la aplicación por primera vez, aparecerá un diálogo para configurar la URL del servidor
2. **Cambiar configuración**: Desde la pantalla de login, toca el botón "Configurar Servidor"
3. **URLs recomendadas**:
   - **Emulador Android**: `http://10.0.2.2:8080` (para servidor en tu ordenador)
   - **Dispositivo físico**: `http://IP_DEL_SERVIDOR:8080` (IP real del servidor en la red)

La configuración se guarda automáticamente en las preferencias de la aplicación.

### 2. Permisos

Los permisos de Internet ya están configurados en el `AndroidManifest.xml`.

### 3. Dependencias

Las dependencias están configuradas en `app/build.gradle`. Ejecuta:

```bash
./gradlew build
```

## Compilación y Ejecución

1. Abre el proyecto en Android Studio
2. Sincroniza el proyecto (Sync Project)
3. Conecta un dispositivo o inicia un emulador
4. Ejecuta la aplicación (Run 'app')

## Requisitos

- Android Studio Hedgehog o superior
- Android SDK 24 (Android 7.0) o superior
- JDK 17
- Backend Spring Boot ejecutándose en `http://localhost:8080`

## Notas

- La aplicación permite configurar la URL del servidor desde la interfaz de usuario
- La URL por defecto es `http://10.0.2.2:8080` (para emulador)
- Para dispositivos físicos, usa la IP real del servidor en la red local
- El cliente Retrofit se actualiza automáticamente cuando cambias la configuración del servidor
- Asegúrate de que el backend tenga CORS configurado para permitir peticiones desde la app

## Funcionalidades Implementadas

### ✅ Completado
- Estructura del proyecto
- Modelos de datos
- Servicios de red (Retrofit)
- Autenticación (Login/Registro)
- Actividad principal con navegación
- Dashboard básico
- Gestión de sesión

### 🚧 Pendiente
- Fragmentos completos (Matches, Bets, Rankings, Profile)
- Adaptadores para RecyclerViews
- Panel de administración
- Validaciones adicionales
- Manejo de errores mejorado
- Caché de datos

## Contribución

Este proyecto es parte de un TFG. Para más información, consulta la documentación del proyecto principal.
