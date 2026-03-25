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
echo [2/3] Creando paquete con jpackage (imagen de app + instalador .exe)...
echo.

set "JP_APP_NAME=NBA Predictor"

REM Crear directorio de salida si no existe
if not exist "dist" mkdir dist

REM jpackage no sobrescribe: eliminar imagen e instalador de ejecuciones anteriores
if exist "%~dp0dist\%JP_APP_NAME%" (
    echo Eliminando imagen anterior: dist\%JP_APP_NAME%
    rd /s /q "%~dp0dist\%JP_APP_NAME%"
)
if exist "%~dp0dist\%JP_APP_NAME%-1.0.0.exe" (
    echo Eliminando instalador anterior...
    del /q "%~dp0dist\%JP_APP_NAME%-1.0.0.exe"
)

REM Paso A: imagen de aplicacion (permite parchear el .cfg antes del instalador)
jpackage ^
    --type app-image ^
    --input target\jpackage-input ^
    --name "%JP_APP_NAME%" ^
    --main-jar TFG-1.0.0.jar ^
    --main-class start.Main ^
    --dest dist ^
    --app-version 1.0.0 ^
    --description "NBA Predictor - Sistema de Predicciones y Apuestas Virtuales" ^
    --vendor "TFG" ^
    --copyright "Copyright 2024" ^
    --java-options --module-path=$APPDIR\javafx ^
    --java-options --add-modules=javafx.controls,javafx.fxml

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: jpackage no pudo generar la imagen de la aplicacion.
    echo Requisitos: JDK 17+ con jpackage. Comprueba que target\jpackage-input contenga javafx\, libs\ y el JAR principal.
    echo.
    echo ALTERNATIVA: Usar Launch4j + Inno Setup (create_exe_launch4j.bat)
    echo.
    pause
    exit /b 1
)

REM Quitar JavaFX del classpath (jpackage lo duplica en classpath y module-path; sin esto puede fallar en algunos JDK).
set "JP_CFG=%~dp0dist\%JP_APP_NAME%\app\%JP_APP_NAME%.cfg"
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0scripts\fix-jpackage-javafx-cfg.ps1" -CfgPath "%JP_CFG%"
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo ajustar el .cfg para JavaFX: "%JP_CFG%"
    echo.
    pause
    exit /b 1
)

REM Paso B: instalador .exe a partir de la imagen ya corregida
jpackage ^
    --type exe ^
    --name "%JP_APP_NAME%" ^
    --app-image "%~dp0dist\%JP_APP_NAME%" ^
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
    echo ERROR: No se pudo crear el instalador .exe (segundo paso de jpackage).
    echo Suelen faltar las herramientas WiX en el PATH: https://wixtoolset.org/
    echo La aplicacion ya esta en: dist\%JP_APP_NAME%\  ^(ejecuta el .exe de esa carpeta^)
    echo.
    echo ALTERNATIVA: Usar Launch4j + Inno Setup
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
