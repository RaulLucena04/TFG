#!/bin/bash
# ============================================
# SCRIPT DE ARRANQUE DEL SERVIDOR (Linux/Mac)
# ============================================
# Este script ARRANCA el backend por sockets (TCP).
# La configuración de MySQL y application.properties se hace con:
#   ./setup_server.sh
# ============================================

set -e

echo "============================================"
echo "ARRANQUE DEL SERVIDOR NBA PREDICTOR (SOCKETS)"
echo "============================================"
echo ""

SOCKET_PORT=9090
if [ -f "src/main/resources/application.properties" ]; then
  P=$(grep -E '^socket\.port=' "src/main/resources/application.properties" | head -n 1 | cut -d'=' -f2 | tr -d '[:space:]')
  if [ -n "$P" ]; then SOCKET_PORT="$P"; fi
fi

if command -v lsof >/dev/null 2>&1; then
  if lsof -iTCP:"$SOCKET_PORT" -sTCP:LISTEN >/dev/null 2>&1; then
    echo "El puerto $SOCKET_PORT ya está en uso. El servidor probablemente ya está ejecutándose."
    echo "Si quieres usar otro puerto, cambia socket.port en src/main/resources/application.properties y reinicia."
    exit 0
  fi
elif command -v ss >/dev/null 2>&1; then
  if ss -ltn | grep -q ":$SOCKET_PORT "; then
    echo "El puerto $SOCKET_PORT ya está en uso. El servidor probablemente ya está ejecutándose."
    echo "Si quieres usar otro puerto, cambia socket.port en src/main/resources/application.properties y reinicia."
    exit 0
  fi
fi

if [ -f "./mvnw" ]; then
  ./mvnw spring-boot:run
else
  mvn spring-boot:run
fi
