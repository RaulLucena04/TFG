@echo off
REM ============================================
REM SCRIPT DE ARRANQUE DEL SERVIDOR (Windows)
REM ============================================
REM Este script ARRANCA el backend por sockets (TCP).
REM La configuración de MySQL y application.properties se hace con:
REM   setup_server.bat
REM ============================================

echo ============================================
echo ARRANQUE DEL SERVIDOR NBA PREDICTOR (SOCKETS)
echo ============================================
echo.

REM Detectar puerto configurado (socket.port) para evitar "Address already in use"
set "SOCKET_PORT=9090"
if exist "src\main\resources\application.properties" (
  for /f "usebackq tokens=1,2 delims==" %%A in (`findstr /i /b "socket.port=" "src\main\resources\application.properties"`) do (
    if not "%%B"=="" set "SOCKET_PORT=%%B"
  )
)

REM Comprobar si ya hay algo escuchando en el puerto
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$p=%SOCKET_PORT%; $c=Get-NetTCPConnection -LocalPort $p -State Listen -ErrorAction SilentlyContinue; if($c){ exit 10 } else { exit 0 }"
if %ERRORLEVEL%==10 (
  echo El puerto %SOCKET_PORT% ya esta en uso. El servidor probablemente ya esta ejecutandose.
  echo Si quieres usar otro puerto, cambia socket.port en src\main\resources\application.properties y reinicia.
  echo.
  pause
  exit /b 0
)

REM Preferir Maven Wrapper si existe
if exist ".\mvnw.cmd" (
  call .\mvnw.cmd spring-boot:run
) else (
  call mvn spring-boot:run
)

if %ERRORLEVEL% NEQ 0 (
  echo.
  echo ERROR: No se pudo iniciar el servidor.
  echo - Si ya estaba arrancado, puede que el puerto %SOCKET_PORT% este ocupado.
  echo - Si no has configurado la base de datos, ejecuta primero: setup_server.bat
  echo.
  pause
  exit /b 1
)
