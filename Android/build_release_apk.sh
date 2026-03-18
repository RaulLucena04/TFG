#!/bin/bash
# ============================================
# SCRIPT PARA GENERAR APK DE RELEASE
# ============================================
# Este script genera el APK de release de la aplicación Android
# ============================================

echo "============================================"
echo "GENERACION DE APK DE RELEASE"
echo "============================================"
echo ""

# Verificar que estamos en la carpeta correcta
if [ ! -f "app/build.gradle" ]; then
    echo "ERROR: Este script debe ejecutarse desde la carpeta Android"
    exit 1
fi

echo "[1/3] Limpiando compilaciones anteriores..."
./gradlew clean
if [ $? -ne 0 ]; then
    echo "ERROR: No se pudo limpiar el proyecto."
    exit 1
fi

echo ""
echo "[2/3] Generando APK de release..."
./gradlew assembleRelease
if [ $? -ne 0 ]; then
    echo "ERROR: No se pudo generar el APK."
    exit 1
fi

echo ""
echo "[3/3] APK generado exitosamente!"
echo ""
echo "El APK se encuentra en:"
echo "app/build/outputs/apk/release/app-release.apk"
echo ""
echo "============================================"
