# MANUAL DE USUARIO
## NBA PREDICTOR - Sistema de Predicciones y Apuestas Virtuales

---

## ÍNDICE

1. [Instalación del Servidor](#1-instalación-del-servidor)
2. [Instalación del Cliente de Escritorio](#2-instalación-del-cliente-de-escritorio)
3. [Instalación de la Aplicación Android](#3-instalación-de-la-aplicación-android)
4. [Uso del Cliente de Escritorio](#4-uso-del-cliente-de-escritorio)
5. [Uso de la Aplicación Android](#5-uso-de-la-aplicación-android)
6. [Configuración](#6-configuración)

---

## 1. INSTALACIÓN DEL SERVIDOR

### 1.1 Requisitos previos

Antes de instalar el servidor, asegúrate de tener instalado:

- **Java JDK 17** o superior
- **MySQL 8.0** o superior
- **Maven 3.6** o superior (opcional, si compilas desde código fuente)

### 1.2 Instalación de MySQL

1. Descarga MySQL desde: https://dev.mysql.com/downloads/mysql/
2. Instala MySQL siguiendo el asistente de instalación
3. Anota la contraseña del usuario `root` que configures durante la instalación

### 1.3 Creación de la base de datos

1. Abre MySQL Workbench o la línea de comandos de MySQL
2. Conéctate con el usuario `root` y tu contraseña
3. Ejecuta el siguiente comando para crear la base de datos:

```sql
CREATE DATABASE nba_app;
```

### 1.4 Configuración del servidor

1. Navega a la carpeta `nba_backend`
2. Abre el archivo `src/main/resources/application.properties`
3. Modifica las siguientes líneas con tus credenciales de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nba_app
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA_AQUI
```

Reemplaza `TU_CONTRASEÑA_AQUI` con la contraseña de MySQL que configuraste.

### 1.5 Ejecución del servidor

**Opción 1: Desde el código fuente (con Maven)**

1. Abre una terminal en la carpeta `nba_backend`
2. Ejecuta el comando:

```bash
mvn spring-boot:run
```

**Opción 2: Ejecutar el JAR compilado**

1. Navega a la carpeta donde esté el archivo JAR del servidor
2. Ejecuta:

```bash
java -jar nba-backend-0.0.1-SNAPSHOT.jar
```

### 1.6 Verificación de la instalación

Si el servidor se ha iniciado correctamente, verás un mensaje similar a:

```
Started NbaBackendApplication in X.XXX seconds
```

El servidor estará disponible en: `http://localhost:8080`

### 1.7 Poblado inicial de datos (Opcional)

Si quieres cargar datos de ejemplo (equipos, jugadores, partidos), ejecuta el script SQL `populate_database.sql` en tu base de datos MySQL.

---

## 2. INSTALACIÓN DEL CLIENTE DE ESCRITORIO

### 2.1 Requisitos previos

- **Java JRE 17** o superior (o JDK 17)
- **Windows 7+**, **Linux** o **macOS**

### 2.2 Instalación de Java

Si no tienes Java instalado:

1. Descarga Java 17 desde: https://adoptium.net/
2. Instala Java siguiendo el asistente
3. Verifica la instalación abriendo una terminal y ejecutando:

```bash
java -version
```

Deberías ver algo como: `openjdk version "17.0.x"`

### 2.3 Ejecución de la aplicación

**Opción 1: Ejecutar el JAR directamente**

1. Navega a la carpeta donde esté el archivo `TFG-1.0.0.jar`
2. Haz doble clic en el archivo o ejecuta desde terminal:

```bash
java -jar TFG-1.0.0.jar
```

**Opción 2: Compilar desde código fuente**

1. Abre una terminal en la carpeta `nba-client`
2. Ejecuta:

```bash
mvn clean package
```

3. El JAR se generará en `target/TFG-1.0.0.jar`
4. Ejecuta el JAR como en la Opción 1

### 2.4 Configuración inicial

La primera vez que ejecutes la aplicación, aparecerá un diálogo para configurar la IP del servidor:

1. Introduce la URL del servidor (ejemplo: `http://192.168.1.100:8080`)
   - Si el servidor está en la misma máquina: `http://localhost:8080`
   - Si el servidor está en otra máquina: `http://IP_DEL_SERVIDOR:8080`
2. Haz clic en "Guardar"
3. La configuración se guardará en el archivo `config.properties` en la misma carpeta

**Nota**: Puedes cambiar la IP del servidor en cualquier momento editando el archivo `config.properties` o ejecutando la aplicación de nuevo.

---

## 3. INSTALACIÓN DE LA APLICACIÓN ANDROID

### 3.1 Requisitos previos

- **Dispositivo Android 7.0 (API 24)** o superior
- **Conexión a Internet** (WiFi o datos móviles)

### 3.2 Instalación desde APK

1. Transfiere el archivo APK a tu dispositivo Android
2. En tu dispositivo, ve a **Configuración > Seguridad**
3. Activa la opción **"Orígenes desconocidos"** o **"Instalar aplicaciones desconocidas"**
4. Abre el archivo APK desde el explorador de archivos
5. Sigue las instrucciones en pantalla para instalar
6. Una vez instalada, encontrarás la aplicación como **"NBA Predictor"**

### 3.3 Instalación desde Android Studio (Desarrolladores)

1. Abre Android Studio
2. Selecciona **File > Open** y navega a la carpeta `Android`
3. Espera a que Android Studio sincronice el proyecto
4. Conecta tu dispositivo Android o inicia un emulador
5. Haz clic en el botón **Run** (▶) o presiona `Shift + F10`
6. Selecciona tu dispositivo/emulador
7. La aplicación se compilará e instalará automáticamente

### 3.4 Configuración inicial

La primera vez que abras la aplicación:

1. Aparecerá un diálogo para configurar la IP del servidor
2. Introduce la URL del servidor:
   - Si el servidor está en tu ordenador y usas emulador: `http://10.0.2.2:8080`
   - Si el servidor está en otra máquina en la misma red: `http://192.168.1.100:8080`
3. Haz clic en **"Guardar"**
4. La configuración se guardará automáticamente

**Nota**: Para cambiar la IP más tarde, puedes reinstalar la aplicación o limpiar los datos de la aplicación en Configuración > Aplicaciones > NBA Predictor > Almacenamiento > Borrar datos.

---

## 4. USO DEL CLIENTE DE ESCRITORIO

### 4.1 Inicio de sesión

1. Al abrir la aplicación, verás la pantalla de **Inicio de sesión**
2. Si no tienes cuenta, haz clic en **"¿No tienes cuenta? Regístrate"**
3. Si ya tienes cuenta:
   - Introduce tu **nombre de usuario**
   - Introduce tu **contraseña**
   - Haz clic en **"Iniciar Sesión"**

### 4.2 Registro de nuevo usuario

1. En la pantalla de inicio de sesión, haz clic en **"¿No tienes cuenta? Regístrate"**
2. Completa el formulario:
   - **Nombre de usuario**: Debe ser único
   - **Email**: Debe ser único y válido
   - **Contraseña**: Mínimo 6 caracteres
   - **Confirmar contraseña**: Debe coincidir con la contraseña
3. Haz clic en **"Registrarse"**
4. Si el registro es exitoso, volverás a la pantalla de inicio de sesión
5. Inicia sesión con tus credenciales

**Nota**: Al registrarte, recibirás **1000 puntos virtuales** iniciales.

### 4.3 Dashboard (Pantalla principal)

Tras iniciar sesión, verás el **Dashboard** con:

- **Puntos disponibles**: Tus puntos virtuales actuales
- **Apuestas activas**: Número de apuestas pendientes de resolver
- **Tasa de aciertos**: Porcentaje de apuestas ganadas
- **Ranking**: Tu posición en el ranking global
- **Próximos partidos**: Lista de partidos programados

### 4.4 Visualización de partidos

1. En el menú lateral, haz clic en **"Partidos"**
2. Verás una lista de todos los partidos con:
   - Equipos (Local vs Visitante)
   - Fecha y hora
   - Estado (Programado, En curso, Finalizado)
   - Resultado (si está finalizado)
3. Haz clic en un partido para ver detalles:
   - Información completa del partido
   - Estadísticas de los equipos
   - Jugadores de cada equipo

### 4.5 Realizar una apuesta

1. Ve a **"Partidos"** y selecciona un partido **programado**
2. Haz clic en **"Crear Apuesta"** o ve directamente a **"Apuestas"** > **"Crear Apuesta"**
3. En el diálogo de crear apuesta:
   - **Selecciona el partido** (si no lo seleccionaste antes)
   - **Elige tu predicción**: LOCAL o VISITANTE
   - **Introduce los puntos** que quieres apostar
   - La **cuota** se calculará automáticamente
   - Verás la **ganancia potencial** si ganas
4. Haz clic en **"Crear Apuesta"**
5. Los puntos se descontarán de tu saldo inmediatamente

**Nota**: Solo puedes apostar en partidos **programados**. No puedes apostar en partidos ya finalizados.

### 4.6 Ver tus apuestas

1. En el menú lateral, haz clic en **"Apuestas"**
2. Verás dos secciones:
   - **Apuestas activas**: Apuestas pendientes de resolver
   - **Historial**: Apuestas ya resueltas (ganadas o perdidas)
3. Cada apuesta muestra:
   - Partido
   - Predicción
   - Puntos apostados
   - Cuota
   - Resultado (si está resuelta)
   - Ganancia (si ganaste)

### 4.7 Rankings

1. En el menú lateral, haz clic en **"Rankings"**
2. Verás la lista de usuarios ordenada por puntos totales
3. Tu posición aparecerá resaltada
4. El ranking se actualiza automáticamente cuando los usuarios ganan apuestas

### 4.8 Estadísticas

1. En el menú lateral, haz clic en **"Estadísticas"**
2. Verás información sobre:
   - Equipos de la NBA
   - Estadísticas de cada equipo (victorias, derrotas, win rate)
   - Jugadores por equipo
3. Puedes filtrar por equipo para ver más detalles

### 4.9 Perfil

1. En el menú lateral, haz clic en **"Perfil"**
2. Verás tu información:
   - Nombre de usuario
   - Email
   - Puntos actuales
   - Estadísticas personales
3. Puedes:
   - **Cambiar contraseña**: Introduce tu nueva contraseña y confírmala
   - **Actualizar puntos**: Sincroniza tus puntos con el servidor

### 4.10 Tienda

1. En el menú lateral, haz clic en **"Tienda"**
2. Aquí puedes canjear puntos virtuales por dinero (simulación PayPal):
   - **Tasa**: 1000 puntos = 1€
   - **Mínimo**: 1000 puntos para canjear
3. Para canjear:
   - Introduce la cantidad de puntos (mínimo 1000)
   - Introduce tu email de PayPal
   - Haz clic en **"Canjear"**
4. Los puntos se descontarán y se simulará la transferencia

**Nota**: Esta es una simulación. En un entorno real, se conectaría con la API de PayPal.

### 4.11 Panel de Administración (Solo administradores)

Si eres administrador, verás la opción **"Administración"** en el menú:

1. Haz clic en **"Administración"**
2. Verás:
   - **Estadísticas generales**: Total de usuarios, partidos activos, etc.
   - **Partidos pendientes**: Partidos que necesitan ser finalizados
3. Para finalizar un partido:
   - Selecciona un partido programado
   - Haz clic en **"Finalizar Partido"**
   - Introduce los puntos del equipo local y visitante
   - Haz clic en **"Finalizar"**
   - El sistema resolverá automáticamente todas las apuestas de ese partido

### 4.12 Cerrar sesión

1. En el menú superior, haz clic en **"Cerrar Sesión"**
2. Volverás a la pantalla de inicio de sesión

---

## 5. USO DE LA APLICACIÓN ANDROID

### 5.1 Inicio de sesión

1. Abre la aplicación **NBA Predictor** en tu dispositivo
2. Si es la primera vez, configura la IP del servidor (ver sección 3.4)
3. En la pantalla de inicio de sesión:
   - Introduce tu **nombre de usuario**
   - Introduce tu **contraseña**
   - Haz clic en **"Iniciar Sesión"**

### 5.2 Registro de nuevo usuario

1. En la pantalla de inicio de sesión, haz clic en **"¿No tienes cuenta? Regístrate"**
2. Completa el formulario:
   - **Usuario**: Nombre de usuario único
   - **Email**: Email único y válido
   - **Contraseña**: Mínimo 6 caracteres
   - **Confirmar Contraseña**: Debe coincidir
3. Haz clic en **"Registrarse"**
4. Si es exitoso, volverás a la pantalla de inicio de sesión

### 5.3 Navegación principal

Tras iniciar sesión, verás la pantalla principal con navegación inferior:

- **🏠 Inicio**: Dashboard con resumen
- **🏀 Partidos**: Lista de partidos
- **💰 Apuestas**: Tus apuestas
- **🏆 Rankings**: Ranking global
- **🛒 Tienda**: Canjear puntos
- **👤 Perfil**: Tu perfil

### 5.4 Dashboard

En la pantalla de **Inicio** verás:

- **Puntos disponibles**: Tus puntos actuales
- **Resumen de estadísticas**: Apuestas activas, ganadas, etc.
- **Próximos partidos**: Partidos programados próximos

### 5.5 Ver partidos

1. Toca **"Partidos"** en la navegación inferior
2. Verás una lista de partidos con:
   - Equipos enfrentados
   - Fecha y hora
   - Estado
3. Toca un partido para ver detalles:
   - Información completa
   - Estadísticas de equipos
   - Opción para crear apuesta

### 5.6 Crear apuesta

1. Ve a **"Partidos"** y toca un partido programado, o
2. Ve a **"Apuestas"** y toca el botón **"+"** (flotante)
3. En el diálogo:
   - **Selecciona el partido** (si no lo seleccionaste antes)
   - **Elige tu predicción**: LOCAL o VISITANTE (chips)
   - **Introduce los puntos** a apostar
   - La **cuota** y **ganancia potencial** se calcularán automáticamente
4. Toca **"Crear Apuesta"**
5. Los puntos se descontarán inmediatamente

**Nota**: El primer partido disponible se selecciona automáticamente si no eliges uno.

### 5.7 Ver tus apuestas

1. Toca **"Apuestas"** en la navegación inferior
2. Verás:
   - **Apuestas activas**: Pendientes de resolver
   - **Historial**: Apuestas resueltas
3. Cada apuesta muestra:
   - Partido
   - Predicción
   - Puntos y cuota
   - Resultado (si está resuelta)

### 5.8 Rankings

1. Toca **"Rankings"** en la navegación inferior
2. Verás la lista de usuarios ordenada por puntos
3. Tu posición aparecerá resaltada si estás en el ranking

### 5.9 Tienda

1. Toca **"Tienda"** en la navegación inferior
2. Para canjear puntos:
   - Introduce los puntos (mínimo 1000)
   - Introduce tu email de PayPal
   - Toca **"Canjear"**
3. Los puntos se descontarán y se simulará la transferencia

### 5.10 Perfil

1. Toca **"Perfil"** en la navegación inferior
2. Verás:
   - Tu información de usuario
   - Puntos actuales
   - Estadísticas personales
3. Puedes:
   - **Cambiar contraseña**
   - **Actualizar puntos**: Sincroniza con el servidor
   - **Cerrar sesión**

### 5.11 Panel de Administración (Solo administradores)

Si eres administrador:

1. En la pantalla principal, verás un botón o menú **"Administración"**
2. Toca para acceder al panel
3. Verás:
   - Estadísticas generales
   - Partidos pendientes de finalizar
4. Para finalizar un partido:
   - Toca un partido programado
   - Toca **"Finalizar"**
   - Introduce los puntos de cada equipo
   - Confirma
   - El sistema resolverá automáticamente las apuestas

### 5.12 Actualizar datos

En cualquier pantalla, puedes:

- **Deslizar hacia abajo** para actualizar (pull to refresh) en algunas pantallas
- Usar el botón **"Actualizar"** si está disponible
- Los datos se sincronizan automáticamente en algunas pantallas

---

## 6. CONFIGURACIÓN

### 6.1 Configuración del servidor (Cliente de escritorio)

La configuración del servidor se guarda en el archivo `config.properties` en la misma carpeta donde ejecutas la aplicación.

**Formato del archivo:**

```properties
#Configuración del servidor
server.url=http://localhost:8080
```

**Para cambiar la IP:**

1. Edita el archivo `config.properties`
2. Cambia la URL por la IP del servidor
3. Guarda el archivo
4. Reinicia la aplicación

**Ejemplos:**

- Servidor en la misma máquina: `http://localhost:8080`
- Servidor en otra máquina de la red local: `http://192.168.1.100:8080`
- Servidor remoto: `http://servidor.dominio.com:8080`

### 6.2 Configuración del servidor (Android)

La configuración se guarda automáticamente en las preferencias de la aplicación.

**Para cambiar la IP:**

1. Desinstala y reinstala la aplicación, o
2. Ve a **Configuración del dispositivo > Aplicaciones > NBA Predictor > Almacenamiento > Borrar datos**
3. Al abrir la aplicación de nuevo, aparecerá el diálogo de configuración

**Ejemplos:**

- Servidor en tu ordenador (usando emulador): `http://10.0.2.2:8080`
- Servidor en otra máquina de la red local: `http://192.168.1.100:8080`
- Servidor remoto: `http://servidor.dominio.com:8080`

### 6.3 Solución de problemas comunes

**Problema: "No se puede conectar al servidor"**

- Verifica que el servidor esté ejecutándose
- Verifica que la IP y el puerto sean correctos
- Verifica que no haya un firewall bloqueando la conexión
- Verifica que el servidor y el cliente estén en la misma red (si es red local)

**Problema: "Error al iniciar sesión"**

- Verifica que el usuario y contraseña sean correctos
- Verifica que el servidor esté funcionando correctamente
- Verifica la conexión a Internet

**Problema: "No se cargan los partidos"**

- Verifica la conexión al servidor
- Verifica que haya partidos en la base de datos
- Intenta actualizar los datos

**Problema: "No puedo crear apuestas"**

- Verifica que tengas suficientes puntos
- Verifica que el partido esté en estado "PROGRAMADO"
- Verifica que el servidor esté funcionando

**Problema: La aplicación Android no se conecta al servidor**

- Si usas emulador, usa `http://10.0.2.2:8080` para servidor en tu ordenador
- Si usas dispositivo físico, usa la IP real del servidor en la red local
- Verifica que el dispositivo y el servidor estén en la misma red WiFi

### 6.4 Requisitos de red

- El servidor debe estar accesible desde el cliente
- Si el servidor está en otra máquina, ambas deben estar en la misma red local o el servidor debe ser accesible desde Internet
- El puerto 8080 debe estar abierto en el servidor
- Si hay firewall, debe permitir conexiones en el puerto 8080

---

## NOTAS FINALES

- **Puntos virtuales**: Los puntos son ficticios y no tienen valor real
- **Simulación PayPal**: La tienda simula la integración con PayPal, no realiza transferencias reales
- **Datos de ejemplo**: Si no cargas datos iniciales, la base de datos estará vacía
- **Soporte**: Para problemas técnicos, consulta la documentación técnica o contacta con el administrador del sistema

---

**Fin del manual**
