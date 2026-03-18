#!/bin/bash
# ============================================
# SCRIPT DE INSTALACIÓN DEL SERVIDOR (Linux/Mac)
# ============================================
# Este script:
# 1. Verifica que MySQL esté instalado
# 2. Crea la base de datos vacía (solo equipos y jugadores)
# 3. Configura el servidor Spring Boot
# ============================================

echo "============================================"
echo "INSTALACION DEL SERVIDOR NBA PREDICTOR"
echo "============================================"
echo ""

# Verificar que MySQL esté instalado
if ! command -v mysql &> /dev/null; then
    echo "ERROR: MySQL no está instalado o no está en el PATH."
    echo "Por favor, instala MySQL primero."
    exit 1
fi

echo "[1/4] Verificando conexión a MySQL..."
echo ""

# Solicitar credenciales de MySQL
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
echo "[2/4] Creando base de datos vacía (solo equipos y jugadores)..."
echo ""

# Crear base de datos vacía
$MYSQL_CMD < create_empty_database.sql
if [ $? -ne 0 ]; then
    echo "ERROR: No se pudo crear la base de datos."
    echo "Verifica tus credenciales de MySQL."
    exit 1
fi

echo "Base de datos creada exitosamente!"
echo ""

echo "[3/4] Configurando application.properties..."
echo ""

# Solicitar configuración para application.properties
read -p "Usuario de base de datos (por defecto: root): " DB_USER
DB_USER=${DB_USER:-root}

read -sp "Contraseña de base de datos: " DB_PASS
echo ""

# Crear backup del archivo original
cp "src/main/resources/application.properties" "src/main/resources/application.properties.backup"

# Actualizar application.properties
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

server.address=0.0.0.0
server.port=8080
EOF

echo "application.properties configurado!"
echo ""

echo "[4/4] Instalación completada!"
echo ""
echo "============================================"
echo "RESUMEN"
echo "============================================"
echo "Base de datos: nba_app"
echo "Estado: Creada con equipos y jugadores"
echo ""
echo "PRÓXIMOS PASOS:"
echo "1. Para poblar la base de datos completa, ejecuta:"
echo "   mysql -u $DB_USER -p nba_app < populate_database.sql"
echo ""
echo "2. Para iniciar el servidor, ejecuta:"
echo "   ./mvnw spring-boot:run"
echo "   O ejecuta el JAR compilado: java -jar target/nba-backend-0.0.1-SNAPSHOT.jar"
echo ""
echo "============================================"
