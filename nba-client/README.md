# NBA Predictor - Cliente de Escritorio

Aplicación de escritorio desarrollada en JavaFX para el sistema de predicciones de partidos de la NBA.

## Características

- ✅ Autenticación (Login/Registro)
- ✅ Dashboard con estadísticas del usuario
- ✅ Visualización de partidos
- ✅ Sistema de apuestas con cuotas
- ✅ Rankings de usuarios
- ✅ Estadísticas de equipos y jugadores
- ✅ Perfil de usuario
- ✅ Tienda virtual (canjear puntos)
- ✅ Panel de administración (solo para admins)

## Tecnologías

- **Java 17** - Lenguaje de programación
- **JavaFX 17** - Interfaz gráfica de usuario
- **Maven** - Gestión de dependencias
- **Jackson** - Serialización JSON para comunicación con API REST

## Estructura del Proyecto

```
nba-client/
├── src/main/java/
│   ├── start/              # Clase principal (Main.java)
│   ├── controller/         # Controladores de vistas
│   │   ├── auth/          # Login y Registro
│   │   ├── layout/        # Layout principal
│   │   ├── dashboard/     # Dashboard
│   │   ├── partidos/      # Partidos
│   │   ├── apuestas/      # Apuestas
│   │   ├── rankings/      # Rankings
│   │   ├── estadisticas/  # Estadísticas
│   │   ├── perfil/        # Perfil
│   │   ├── tienda/        # Tienda
│   │   └── admin/         # Administración
│   ├── model/             # Modelos de datos
│   ├── service/           # Servicios de comunicación con API
│   ├── session/           # Gestión de sesión
│   └── util/              # Utilidades (Config, etc.)
├── src/main/resources/
│   └── ui/                # Archivos FXML de las vistas
└── config.properties       # Configuración del servidor (se crea automáticamente)
```

## Configuración

### 1. URL del Backend

La URL del servidor se configura automáticamente la primera vez que ejecutas la aplicación:

1. Al iniciar la aplicación, aparecerá un diálogo para configurar la IP del servidor
2. Introduce la URL del servidor (ejemplo: `http://192.168.1.100:8080`)
   - Si el servidor está en la misma máquina: `http://localhost:8080`
   - Si el servidor está en otra máquina: `http://IP_DEL_SERVIDOR:8080`
3. La configuración se guarda en `config.properties` en la misma carpeta

**Cambiar configuración:**
- Edita el archivo `config.properties` manualmente, o
- Ejecuta la aplicación de nuevo y aparecerá el diálogo de configuración

## Compilación y Ejecución

### Requisitos

- Java JDK 17 o superior
- Maven 3.6+ (para compilar desde código fuente)

### Compilar desde código fuente

```bash
cd nba-client
mvn clean package
```

El JAR se generará en `target/TFG-1.0.0.jar`

### Ejecutar

**Opción 1: Ejecutar JAR directamente**
```bash
java -jar target/TFG-1.0.0.jar
```

**Opción 2: Ejecutar con Maven**
```bash
mvn javafx:run
```

### Crear instalador .exe

Ver `README_INSTALADOR.md` para instrucciones detalladas sobre cómo crear un instalador .exe para Windows.

## Funcionalidades

### Gestión de Usuarios
- Registro de nuevos usuarios
- Inicio de sesión
- Cambio de contraseña
- Gestión de perfil

### Sistema de Apuestas
- Visualización de partidos programados
- Creación de apuestas con cálculo automático de cuotas
- Visualización de apuestas activas e historial
- Resolución automática de apuestas cuando se finalizan partidos

### Estadísticas y Rankings
- Visualización de estadísticas de equipos
- Información de jugadores por equipo
- Rankings globales de usuarios

### Panel de Administración
- Gestión de partidos (crear, finalizar)
- Gestión de usuarios
- Estadísticas generales del sistema

### Tienda Virtual
- Canjear puntos virtuales por dinero (simulación PayPal)
- Tasa de conversión: 1000 puntos = 1€
- Mínimo: 1000 puntos para canjear

## Notas

- La aplicación requiere que el servidor Spring Boot esté ejecutándose
- La configuración del servidor se guarda en `config.properties`
- Los puntos son virtuales y no tienen valor real
- La integración con PayPal es una simulación

## Solución de Problemas

**Error: "No se puede conectar al servidor"**
- Verifica que el servidor esté ejecutándose
- Verifica la URL en `config.properties`
- Verifica que no haya firewall bloqueando el puerto 8080

**Error: "Java no encontrado"**
- Asegúrate de tener Java 17+ instalado
- Verifica que Java esté en el PATH del sistema

## Contribución

Este proyecto es parte de un TFG. Para más información, consulta la documentación del proyecto principal.
