# GUÍA DE INSTALACIÓN - NBA PREDICTOR

Esta guía explica cómo instalar y configurar todas las aplicaciones del proyecto NBA Predictor.

## ESTRUCTURA DEL PROYECTO

```
TFG-main/
├── nba_backend/          # Servidor Spring Boot
├── nba-client/           # Cliente de escritorio JavaFX
├── Android/              # Aplicación Android
└── Documentacion/        # Documentación del proyecto
```

## 1. INSTALACIÓN DEL SERVIDOR

### Requisitos previos:
- Java JDK 17 o superior
- MySQL 8.0 o superior
- Maven 3.6+ (opcional, si compilas desde código)

### Windows:

1. Abre una terminal en la carpeta `nba_backend`
2. Ejecuta:
   ```bash
   setup_server.bat
   ```
3. Sigue las instrucciones en pantalla:
   - Introduce tus credenciales de MySQL
   - El script creará la base de datos vacía (solo equipos y jugadores)
   - Configurará `application.properties`

### Linux/Mac:

1. Abre una terminal en la carpeta `nba_backend`
2. Da permisos de ejecución:
   ```bash
   chmod +x install_server.sh
   ```
3. Ejecuta:
   ```bash
   ./setup_server.sh
   ```
4. Sigue las instrucciones en pantalla

### Poblar base de datos completa (Opcional):

Después de la instalación básica, puedes poblar la base de datos con datos de ejemplo:

```bash
mysql -u root -p nba_app < populate_database.sql
```

Esto añadirá:
- Usuarios de ejemplo
- Partidos de ejemplo
- Apuestas de ejemplo

### Iniciar el servidor:

**Opción 1: Script (recomendado)**
```bash
nba_backend/install_server.bat
```

En Linux/Mac:

```bash
./install_server.sh
```

**Opción 2: Con Maven**
```bash
mvn spring-boot:run
```

**Opción 2: Con JAR compilado**
```bash
mvn clean package
java -jar target/nba-backend-0.0.1-SNAPSHOT.jar
```

El servidor escuchará por sockets en: `localhost:9090` (configurable en `socket.port`)

## 2. INSTALACIÓN DEL CLIENTE DE ESCRITORIO

### Requisitos previos:
- Java JRE 17 o superior (o JDK 17)
- Windows 7+, Linux o macOS

### Crear instalador .exe:

#### Opción 1: Usando jpackage (Recomendado)

1. Abre una terminal en la carpeta `nba-client`
2. Ejecuta:
   ```bash
   create_exe_installer.bat
   ```
3. El instalador se generará en `dist/NBA Predictor-1.0.0.exe`

#### Opción 2: Usando Launch4j + Inno Setup

1. Ejecuta:
   ```bash
   create_exe_launch4j.bat
   ```
2. Usa Inno Setup para crear el instalador final

Ver `nba-client/README_INSTALADOR.md` para más detalles.

### Instalar y ejecutar:

1. Ejecuta el instalador `.exe` generado
2. Sigue el asistente de instalación
3. La primera vez que ejecutes la aplicación, configura la IP del servidor:
   - Si el servidor está en la misma máquina: `localhost:9090`
   - Si el servidor está en otra máquina: `IP_DEL_SERVIDOR:9090`

### Ejecutar sin instalador (desarrollo):

```bash
mvn clean package
java -jar target/TFG-1.0.0.jar
```

## 3. INSTALACIÓN DE LA APLICACIÓN ANDROID

### Generar APK:

#### APK de Debug (desarrollo):

Tras `gradlew assembleDebug`, el APK queda bajo `%LOCALAPPDATA%\TFG-APK\build\...` (ruta interna de Gradle).  
Para una copia **sencilla** en el proyecto, desde la carpeta `Android` ejecuta:

```bash
build_release_apk.bat debug
```

(o `./build_release_apk.sh debug`). El archivo quedará en: `Android/apk/NBA-Predictor-debug.apk`

#### APK de Release (producción):

1. Abre una terminal en la carpeta `Android`
2. Ejecuta:
   ```bash
   build_release_apk.bat
   ```
   O en Linux/Mac:
   ```bash
   chmod +x build_release_apk.sh
   ./build_release_apk.sh
   ```
   Para **debug**: añade `debug` como argumento (ver arriba).
3. El script **borra** los `.apk` antiguos en `Android/apk/`, compila y deja el APK en:
   - **Release:** `Android/apk/NBA-Predictor-release.apk`
   - **Debug:** `Android/apk/NBA-Predictor-debug.apk`  
   La compilación intermedia usa la ruta corta `%LOCALAPPDATA%\TFG-APK\build` (Windows) o `~/.tfg/apk-build` (Linux/Mac).

### Instalar APK:

#### En dispositivo físico:

1. Transfiere el archivo APK a tu dispositivo Android
2. En tu dispositivo, ve a **Configuración > Seguridad**
3. Activa **"Orígenes desconocidos"** o **"Instalar aplicaciones desconocidas"**
4. Abre el archivo APK desde el explorador de archivos
5. Sigue las instrucciones para instalar

#### En emulador:

1. Arrastra el APK al emulador, o
2. Usa ADB:
   ```bash
   adb install -r Android/apk/NBA-Predictor-release.apk
   ```

### Configuración inicial:

La primera vez que abras la aplicación:
1. Aparecerá un diálogo para configurar la IP del servidor
2. Introduce la URL:
   - Emulador: `http://10.0.2.2:8080` (servidor en tu ordenador)
   - Dispositivo físico: `http://IP_DEL_SERVIDOR:8080` (IP real del servidor en la red)

## RESUMEN DE ARCHIVOS DE INSTALACIÓN

### Servidor:
- **Script de configuración Windows**: `nba_backend/setup_server.bat`
- **Script de configuración Linux/Mac**: `nba_backend/setup_server.sh`
- **Script de arranque Windows**: `nba_backend/install_server.bat`
- **Script de arranque Linux/Mac**: `nba_backend/install_server.sh`
- **Base de datos vacía**: `nba_backend/create_empty_database.sql`
- **Poblar base de datos completa**: `nba_backend/populate_database.sql`

### Cliente de escritorio:
- **Crear instalador .exe (jpackage)**: `nba-client/create_exe_installer.bat`
- **Crear .exe (Launch4j)**: `nba-client/create_exe_launch4j.bat`
- **Documentación**: `nba-client/README_INSTALADOR.md`

### Android:
- **Script APK (release / debug)**: `Android/build_release_apk.bat` o `Android/build_release_apk.sh` (argumento opcional `debug`)
- **APK listo para instalar**: `Android/apk/NBA-Predictor-release.apk` o `Android/apk/NBA-Predictor-debug.apk` (la carpeta `apk/` se limpia en cada ejecución del script)
- **Salida interna de Gradle** (Windows): `%LOCALAPPDATA%\TFG-APK\build\outputs\apk\...`

## ORDEN DE INSTALACIÓN RECOMENDADO

1. **Instalar y configurar el servidor** (MySQL + Spring Boot)
2. **Crear base de datos vacía** (equipos y jugadores)
3. **Iniciar el servidor**
4. **Instalar cliente de escritorio** (.exe)
5. **Instalar aplicación Android** (APK)
6. **Configurar IP del servidor** en ambas aplicaciones cliente
7. **(Opcional) Poblar base de datos completa** con datos de ejemplo

## SOLUCIÓN DE PROBLEMAS

### El servidor no inicia:
- Verifica que MySQL esté ejecutándose
- Verifica las credenciales en `application.properties`
- Verifica que el puerto 8080 esté libre

### El cliente no se conecta al servidor:
- Verifica que el servidor esté ejecutándose
- Verifica la IP/puerto configurados en `config.properties` (cliente) o en la app (Android)
- Verifica que no haya firewall bloqueando el puerto 9090

### El instalador .exe no funciona:
- Verifica que Java 17+ esté instalado
- Si usas jpackage, verifica que esté en el PATH
- Usa la alternativa con Launch4j si jpackage no funciona

### El APK no se instala:
- Verifica que "Orígenes desconocidos" esté activado
- Verifica que el dispositivo tenga Android 7.0 (API 24) o superior
- Intenta desinstalar versiones anteriores primero

## CONTACTO Y SOPORTE

Para más información, consulta:
- **Análisis del Proyecto**: `Documentacion/Analisis del proyecto/Analisis del Proyecto.md`
- **Manual de Usuario**: `Documentacion/Manual de usuario/Manual de Usuario.md`
