@echo off
setlocal EnableExtensions
REM ============================================================
REM Genera APK (release por defecto; "debug" como primer argumento)
REM - Limpia APK antiguos en la carpeta simple Android\apk
REM - Ejecuta gradlew clean + assemble*
REM - Copia el APK generado a Android\apk\ (nombre fijo, facil de encontrar)
REM ============================================================

cd /d "%~dp0"
if not exist "app\build.gradle" (
    echo ERROR: Ejecuta este archivo desde la carpeta Android del proyecto.
    exit /b 1
)

set "MODE=%~1"
if /I "%MODE%"=="debug" (
    set "GRADLE_TASK=assembleDebug"
    set "SRC_SUBDIR=debug"
    set "SRC_NAME=app-debug.apk"
    set "OUT_NAME=NBA-Predictor-debug.apk"
) else (
    set "MODE=release"
    set "GRADLE_TASK=assembleRelease"
    set "SRC_SUBDIR=release"
    set "SRC_NAME=app-release.apk"
    set "OUT_NAME=NBA-Predictor-release.apk"
)

set "DIST=%~dp0apk"
set "GRADLE_BUILD=%LOCALAPPDATA%\TFG-APK\build"
set "GRADLE_APK=%GRADLE_BUILD%\outputs\apk\%SRC_SUBDIR%\%SRC_NAME%"
set "OUT_APK=%DIST%\%OUT_NAME%"

echo ============================================================
echo APK ^(%MODE%^)
echo Carpeta de salida simple: %DIST%
echo ============================================================
echo.

if not exist "%DIST%" mkdir "%DIST%"
echo [1/4] Borrando APK anteriores en "%DIST%"...
del /q "%DIST%\*.apk" 2>nul

echo.
echo [2/4] gradlew clean...
call gradlew.bat clean
if errorlevel 1 (
    echo ERROR: clean fallo.
    exit /b 1
)

echo.
echo [3/4] gradlew %GRADLE_TASK%...
call gradlew.bat %GRADLE_TASK%
if errorlevel 1 (
    echo ERROR: compilacion fallo.
    exit /b 1
)

if not exist "%GRADLE_APK%" (
    echo ERROR: No se encontro el APK generado en:
    echo   %GRADLE_APK%
    exit /b 1
)

echo.
echo [4/4] Copiando a carpeta apk del proyecto...
copy /Y "%GRADLE_APK%" "%OUT_APK%" >nul
if errorlevel 1 (
    echo ERROR: No se pudo copiar a %OUT_APK%
    exit /b 1
)

echo.
echo Listo.
echo   Origen Gradle: %GRADLE_APK%
echo   Copia sencilla: %OUT_APK%
echo.
echo Para instalar / sustituir en el telefono ^(USB, depuracion USB^):
echo   adb uninstall com.tfg.nbapredictor
echo   adb install -r "%OUT_APK%"
echo ============================================================
pause
