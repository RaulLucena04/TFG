@echo off
REM ============================================
REM ALTERNATIVA: CREAR .EXE CON LAUNCH4J
REM ============================================
REM Si jpackage no funciona, usa este script
REM Requiere: Launch4j instalado
REM ============================================

echo ============================================
echo CREACION DE .EXE CON LAUNCH4J
echo ============================================
echo.
echo NOTA: Este script requiere Launch4j instalado.
echo Descarga desde: http://launch4j.sourceforge.net/
echo.

REM Verificar que Launch4j esté instalado
set LAUNCH4J_PATH=C:\Program Files\Launch4j\launch4j.exe
if not exist "%LAUNCH4J_PATH%" (
    echo ERROR: Launch4j no se encuentra en la ruta por defecto.
    echo Por favor, instala Launch4j o modifica LAUNCH4J_PATH en este script.
    pause
    exit /b 1
)

REM Compilar el proyecto primero
echo [1/2] Compilando el proyecto...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: La compilacion fallo.
    pause
    exit /b 1
)

echo.
echo [2/2] Creando .exe con Launch4j...
echo.

REM Crear archivo de configuración Launch4j
REM El JAR principal y todas las dependencias estan en dist\ ^(Maven package^). JavaFX exige --module-path.
(
echo ^<?xml version="1.0" encoding="UTF-8"?^>
echo ^<launch4jConfig^>
echo   ^<dontWrapJar^>true^</dontWrapJar^>
echo   ^<headerType^>gui^</headerType^>
echo   ^<jar^>TFG-1.0.0.jar^</jar^>
echo   ^<outfile^>dist\NBA_Predictor.exe^</outfile^>
echo   ^<errTitle^>NBA Predictor^</errTitle^>
echo   ^<classPath^>
echo     ^<mainClass^>start.Main^</mainClass^>
echo     ^<cp^>TFG-1.0.0.jar^</cp^>
echo   ^</classPath^>
echo   ^<cmdLine^>^</cmdLine^>
echo   ^<chdir^>.^</chdir^>
echo   ^<priority^>normal^</priority^>
echo   ^<downloadUrl^>https://adoptium.net/^</downloadUrl^>
echo   ^<supportUrl^>^</supportUrl^>
echo   ^<stayAlive^>false^</stayAlive^>
echo   ^<restartOnCrash^>false^</restartOnCrash^>
echo   ^<manifest^>^</manifest^>
echo   ^<icon^>^</icon^>
echo   ^<jre^>
echo     ^<path^>^</path^>
echo     ^<bundledJre64Bit^>false^</bundledJre64Bit^>
echo     ^<bundledJreAsFallback^>false^</bundledJreAsFallback^>
echo     ^<minVersion^>17^</minVersion^>
echo     ^<maxVersion^>^</maxVersion^>
echo     ^<jdkPreference^>preferJre^</jdkPreference^>
echo     ^<runtimeBits^>64/32^</runtimeBits^>
echo     ^<opts^>
echo       ^<opt^>--module-path^</opt^>
echo       ^<opt^>lib^</opt^>
echo       ^<opt^>--add-modules^</opt^>
echo       ^<opt^>javafx.controls,javafx.fxml^</opt^>
echo     ^</opts^>
echo   ^</jre^>
echo   ^<versionInfo^>
echo     ^<fileVersion^>1.0.0.0^</fileVersion^>
echo     ^<txtFileVersion^>1.0.0^</txtFileVersion^>
echo     ^<fileDescription^>NBA Predictor - Sistema de Predicciones y Apuestas Virtuales^</fileDescription^>
echo     ^<copyright^>Copyright 2024^</copyright^>
echo     ^<productVersion^>1.0.0.0^</productVersion^>
echo     ^<txtProductVersion^>1.0.0^</txtProductVersion^>
echo     ^<productName^>NBA Predictor^</productName^>
echo     ^<companyName^>TFG^</companyName^>
echo     ^<internalName^>NBA_Predictor^</internalName^>
echo     ^<originalFilename^>NBA_Predictor.exe^</originalFilename^>
echo   ^</versionInfo^>
echo ^</launch4jConfig^>
) > launch4j_config.xml

REM Ejecutar Launch4j
"%LAUNCH4J_PATH%" launch4j_config.xml

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: No se pudo crear el .exe con Launch4j.
    pause
    exit /b 1
)

echo.
echo .exe creado exitosamente en: dist\NBA_Predictor.exe
echo.
echo NOTA: Para crear un instalador completo, usa Inno Setup con el .exe generado.
echo.
pause
