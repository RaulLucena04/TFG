@echo off
REM ============================================
REM SCRIPT PARA GENERAR APK DE RELEASE
REM ============================================
REM Este script genera el APK de release de la aplicación Android
REM ============================================

echo ============================================
echo GENERACION DE APK DE RELEASE
echo ============================================
echo.

REM Verificar que estamos en la carpeta correcta
if not exist "app\build.gradle" (
    echo ERROR: Este script debe ejecutarse desde la carpeta Android
    pause
    exit /b 1
)

echo [1/3] Limpiando compilaciones anteriores...
call gradlew clean
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo limpiar el proyecto.
    pause
    exit /b 1
)

echo.
echo [2/3] Generando APK de release...
call gradlew assembleRelease
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo generar el APK.
    pause
    exit /b 1
)

echo.
echo [3/3] APK generado exitosamente!
echo.
echo El APK se encuentra en:
echo app\build\outputs\apk\release\app-release.apk
echo.
echo ============================================
pause
