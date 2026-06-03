#!/usr/bin/env bash
# ============================================================
# Genera APK (release por defecto; pasa "debug" como argumento)
# - Borra APK anteriores en Android/apk/
# - gradlew clean + assemble*
# - Copia el APK a Android/apk/ con nombre fijo
# ============================================================
set -euo pipefail
cd "$(dirname "$0")"

if [[ ! -f app/build.gradle ]]; then
  echo "ERROR: Ejecuta desde la carpeta Android del proyecto."
  exit 1
fi

MODE="${1:-release}"
case "$(echo "$MODE" | tr '[:upper:]' '[:lower:]')" in
  debug)
    GRADLE_TASK="assembleDebug"
    SRC_SUBDIR="debug"
    SRC_NAME="app-debug.apk"
    OUT_NAME="NBA-Predictor-debug.apk"
    ;;
  *)
    MODE="release"
    GRADLE_TASK="assembleRelease"
    SRC_SUBDIR="release"
    SRC_NAME="app-release.apk"
    OUT_NAME="NBA-Predictor-release.apk"
    ;;
esac

DIST="$(pwd)/apk"
if [[ -n "${LOCALAPPDATA:-}" ]]; then
  GRADLE_BUILD="${LOCALAPPDATA}/TFG-APK/build"
else
  GRADLE_BUILD="${HOME}/.tfg/apk-build"
fi
GRADLE_APK="${GRADLE_BUILD}/outputs/apk/${SRC_SUBDIR}/${SRC_NAME}"
OUT_APK="${DIST}/${OUT_NAME}"

echo "============================================================"
echo "APK (${MODE})"
echo "Carpeta de salida: ${DIST}"
echo "============================================================"

mkdir -p "${DIST}"
echo "[1/4] Borrando APK anteriores en ${DIST}..."
rm -f "${DIST}"/*.apk

echo ""
echo "[2/4] ./gradlew clean..."
./gradlew clean

echo ""
echo "[3/4] ./gradlew ${GRADLE_TASK}..."
./gradlew "${GRADLE_TASK}"

if [[ ! -f "${GRADLE_APK}" ]]; then
  echo "ERROR: No se encontró el APK en: ${GRADLE_APK}"
  exit 1
fi

echo ""
echo "[4/4] Copiando a carpeta apk del proyecto..."
cp -f "${GRADLE_APK}" "${OUT_APK}"

echo ""
echo "Listo."
echo "  Origen Gradle: ${GRADLE_APK}"
echo "  Copia sencilla: ${OUT_APK}"
echo ""
echo "Para instalar / sustituir en el teléfono (USB, depuración USB):"
echo "  adb uninstall com.tfg.nbapredictor"
echo "  adb install -r \"${OUT_APK}\""
echo "============================================================"
