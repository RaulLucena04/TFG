@echo off
REM ============================================
REM SCRIPT DE CONFIGURACIÓN DEL SERVIDOR (Windows)
REM ============================================
REM Este script:
REM 1. Verifica que MySQL esté instalado
REM 2. Crea la base de datos vacía (solo equipos y jugadores)
REM 3. Configura application.properties (JPA + PayPal + sockets)
REM ============================================

echo ============================================
echo CONFIGURACION DEL SERVIDOR NBA PREDICTOR
echo ============================================
echo.

REM Verificar que MySQL esté en el PATH
where mysql >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: MySQL no se encuentra en el PATH.
    echo Por favor, asegurate de que MySQL este instalado y agregado al PATH del sistema.
    echo.
    pause
    exit /b 1
)

echo [1/4] Verificando conexion a MySQL...
echo.

REM Solicitar credenciales de MySQL
set /p MYSQL_USER="Usuario de MySQL (por defecto: root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASS="Contrasena de MySQL: "
if "%MYSQL_PASS%"=="" (
    echo Usando conexion sin contrasena...
    set MYSQL_CMD=mysql -u %MYSQL_USER%
) else (
    set MYSQL_CMD=mysql -u %MYSQL_USER% -p%MYSQL_PASS%
)

echo.
echo [2/4] Creando base de datos vacia (solo equipos y jugadores)...
echo.

%MYSQL_CMD% < create_empty_database.sql
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo crear la base de datos.
    echo Verifica tus credenciales de MySQL.
    echo.
    pause
    exit /b 1
)

echo Base de datos creada exitosamente!
echo.

echo [3/4] Configurando application.properties...
echo.

set /p DB_USER="Usuario de base de datos (por defecto: root): "
if "%DB_USER%"=="" set DB_USER=root

set /p DB_PASS="Contrasena de base de datos: "

set /p SOCKET_PORT="Puerto del socket (por defecto: 9090): "
if "%SOCKET_PORT%"=="" set SOCKET_PORT=9090

copy "src\main\resources\application.properties" "src\main\resources\application.properties.backup" >nul

(
echo spring.application.name=nba-backend
echo spring.datasource.url=jdbc:mysql://localhost:3306/nba_app
echo spring.datasource.username=%DB_USER%
echo spring.datasource.password=%DB_PASS%
echo.
echo spring.jpa.hibernate.ddl-auto=update
echo spring.jpa.show-sql=true
echo spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
echo.
echo # Configuracion de PayPal ^(obtener desde https://developer.paypal.com/dashboard/^)
echo # Para usar la API real, configura estas credenciales:
echo # paypal.client.id=TU_CLIENT_ID
echo # paypal.client.secret=TU_CLIENT_SECRET
echo # paypal.mode=sandbox ^(o production para produccion^)
echo paypal.client.id=AVkeujc-n43-Iv57R_jeYF10Tz6zDwYNWVpjtGAKCwjiSZj_QpMgDCdi1fymHElDo2P-WwhWPeyy4aZQ
echo paypal.client.secret=EHJpUbA1v2a-pK6yQbSX1U88Li6plSH_EJWxzvi8tN6ZuN-XHiO-Pn3GVeWmJTy_mI-amAFlzSGX7eB_
echo paypal.mode=sandbox
echo.
echo socket.bindAddress=0.0.0.0
echo socket.port=%SOCKET_PORT%
) > "src\main\resources\application.properties"

echo application.properties configurado!
echo.

echo [4/4] Configuracion completada!
echo.
echo ============================================
echo RESUMEN
echo ============================================
echo Base de datos: nba_app
echo Puerto socket: %SOCKET_PORT%
echo.
echo PROXIMOS PASOS:
echo 1. Para poblar la base de datos completa, ejecuta:
echo    mysql -u %DB_USER% -p nba_app ^< populate_database.sql
echo.
echo 2. Para arrancar el servidor, ejecuta:
echo    install_server.bat
echo.
echo ============================================
pause

