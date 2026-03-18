@echo off
REM ============================================
REM SCRIPT PARA CREAR INSTALADOR .EXE
REM ============================================
REM Este script crea un instalador .exe usando jpackage
REM Requiere: Java JDK 17+ con jpackage
REM ============================================

echo ============================================
echo CREACION DE INSTALADOR .EXE
echo ============================================
echo.

REM Verificar que Java esté instalado
where java >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java no se encuentra en el PATH.
    echo Por favor, instala Java JDK 17 o superior.
    pause
    exit /b 1
)

REM Verificar versión de Java
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    goto :check_version
)
:check_version

echo Verificando version de Java...
java -version
echo.

REM Verificar que jpackage esté disponible
where jpackage >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ADVERTENCIA: jpackage no se encuentra en el PATH.
    echo Asegurate de tener Java JDK 17+ instalado.
    echo.
    echo Alternativa: Usar Launch4j + Inno Setup
    echo.
    pause
)

REM Compilar el proyecto primero
echo [1/3] Compilando el proyecto...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: La compilacion fallo.
    pause
    exit /b 1
)

echo.
echo [2/3] Creando instalador .exe con jpackage...
echo.

REM Crear directorio de salida si no existe
if not exist "dist" mkdir dist

REM Crear el instalador con jpackage
jpackage ^
    --input target ^
    --name "NBA Predictor" ^
    --main-jar TFG-1.0.0.jar ^
    --main-class start.Main ^
    --type exe ^
    --dest dist ^
    --app-version 1.0.0 ^
    --description "NBA Predictor - Sistema de Predicciones y Apuestas Virtuales" ^
    --vendor "TFG" ^
    --copyright "Copyright 2024" ^
    --win-dir-chooser ^
    --win-menu ^
    --win-shortcut

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: No se pudo crear el instalador con jpackage.
    echo.
    echo ALTERNATIVA: Usar Launch4j + Inno Setup
    echo 1. Descarga Launch4j: http://launch4j.sourceforge.net/
    echo 2. Crea un wrapper .exe del JAR
    echo 3. Usa Inno Setup para crear el instalador
    echo.
    pause
    exit /b 1
)

echo.
echo [3/3] Instalador creado exitosamente!
echo.
echo El instalador se encuentra en: dist\NBA Predictor-1.0.0.exe
echo.
echo ============================================
pause
