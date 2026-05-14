#!/bin/bash
# ============================================
# SCRIPT DE CONFIGURACIÓN DEL SERVIDOR (Linux/Mac)
# ============================================
# Este script:
# 1. Verifica que MySQL esté instalado
# 2. Crea la base de datos vacía (solo equipos y jugadores)
# 3. Configura application.properties (JPA + PayPal + sockets)
# ============================================

set -e

echo "============================================"
echo "CONFIGURACION DEL SERVIDOR NBA PREDICTOR"
echo "============================================"
echo ""

if ! command -v mysql &> /dev/null; then
    echo "ERROR: MySQL no está instalado o no está en el PATH."
    echo "Por favor, instala MySQL primero."
    exit 1
fi

read -p "Usuario de MySQL (por defecto: root): " MYSQL_USER
MYSQL_USER=${MYSQL_USER:-root}

read -sp "Contraseña de MySQL: " MYSQL_PASS
echo ""

if [ -z "$MYSQL_PASS" ]; then
    MYSQL_CMD="mysql -u $MYSQL_USER"
else
    MYSQL_CMD="mysql -u $MYSQL_USER -p$MYSQL_PASS"
fi

echo ""
echo "[1/3] Creando base de datos vacía (solo equipos y jugadores)..."
echo ""

$MYSQL_CMD < create_empty_database.sql

echo ""
echo "[2/3] Configurando application.properties..."
echo ""

read -p "Usuario de base de datos (por defecto: root): " DB_USER
DB_USER=${DB_USER:-root}

read -sp "Contraseña de base de datos: " DB_PASS
echo ""

read -p "Puerto del socket (por defecto: 9090): " SOCKET_PORT
SOCKET_PORT=${SOCKET_PORT:-9090}

cp "src/main/resources/application.properties" "src/main/resources/application.properties.backup" || true

cat > "src/main/resources/application.properties" << EOF
spring.application.name=nba-backend
spring.datasource.url=jdbc:mysql://localhost:3306/nba_app
spring.datasource.username=$DB_USER
spring.datasource.password=$DB_PASS

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configuración de PayPal (obtener desde https://developer.paypal.com/dashboard/)
# Para usar la API real, configura estas credenciales:
# paypal.client.id=TU_CLIENT_ID
# paypal.client.secret=TU_CLIENT_SECRET
# paypal.mode=sandbox (o production para producción)
paypal.client.id=AVkeujc-n43-Iv57R_jeYF10Tz6zDwYNWVpjtGAKCwjiSZj_QpMgDCdi1fymHElDo2P-WwhWPeyy4aZQ
paypal.client.secret=EHJpUbA1v2a-pK6yQbSX1U88Li6plSH_EJWxzvi8tN6ZuN-XHiO-Pn3GVeWmJTy_mI-amAFlzSGX7eB_
paypal.mode=sandbox

socket.bindAddress=0.0.0.0
socket.port=$SOCKET_PORT
EOF

echo ""
echo "[3/3] Configuracion completada."
echo ""
echo "Para arrancar el servidor: ./install_server.sh"

