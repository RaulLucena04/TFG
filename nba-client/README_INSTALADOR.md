# CREACIÓN DE INSTALADOR .EXE PARA CLIENTE DE ESCRITORIO

Este documento explica cómo crear el instalador .exe para el cliente de escritorio.

## OPCIÓN 1: Usando jpackage (Recomendado - Java 17+)

### Requisitos:
- Java JDK 17 o superior
- Maven instalado

### Pasos:

1. Abre una terminal en la carpeta `nba-client`
2. Ejecuta el script:
   ```bash
   create_exe_installer.bat
   ```
3. El instalador se generará en `dist/NBA Predictor-1.0.0.exe`

### Notas:
- jpackage está incluido en Java JDK 17+
- Crea un instalador completo con todas las dependencias
- El instalador incluye Java embebido (opcional)

## OPCIÓN 2: Usando Launch4j + Inno Setup

### Requisitos:
- Launch4j instalado (http://launch4j.sourceforge.net/)
- Inno Setup instalado (https://jrsoftware.org/isinfo.php)
- Maven instalado

### Pasos:

1. **Crear .exe con Launch4j:**
   ```bash
   create_exe_launch4j.bat
   ```
   Esto creará `dist/NBA_Predictor.exe`

2. **Crear instalador con Inno Setup:**
   - Abre Inno Setup
   - Crea un nuevo script usando el asistente
   - Configura:
     - Nombre: NBA Predictor
     - Versión: 1.0.0
     - Archivo ejecutable: `dist/NBA_Predictor.exe`
     - Incluir: `dist/NBA_Predictor.exe` y `config.properties` (opcional)
   - Compila el instalador

### Ventajas:
- Más control sobre el instalador
- Puedes incluir archivos adicionales
- Instalador más personalizable

## OPCIÓN 3: Manual (Solo .exe sin instalador)

Si solo necesitas un .exe ejecutable:

1. Compila el proyecto:
   ```bash
   mvn clean package
   ```

2. Usa Launch4j para crear el .exe:
   - Abre Launch4j
   - Configura:
     - Output file: `dist/NBA_Predictor.exe`
     - Jar: `target/TFG-1.0.0.jar`
     - Min JRE version: 17
   - Build wrapper

## ESTRUCTURA DEL INSTALADOR

El instalador debe incluir:
- El ejecutable .exe
- El archivo JAR (si no está embebido)
- El archivo `config.properties` (opcional, se crea automáticamente)
- Documentación (opcional)

## NOTAS IMPORTANTES

- El usuario debe tener Java 17+ instalado (a menos que uses jpackage con Java embebido)
- El archivo `config.properties` se crea automáticamente la primera vez que se ejecuta
- El instalador debe ejecutarse con permisos de administrador si se instala en Program Files

## SOLUCIÓN DE PROBLEMAS

**Error: "jpackage no encontrado"**
- Asegúrate de tener Java JDK 17+ instalado
- Verifica que jpackage esté en el PATH

**Error: "No se puede ejecutar el .exe"**
- Verifica que Java esté instalado en el sistema
- Verifica que la versión de Java sea 17 o superior

**El instalador no incluye Java:**
- Usa jpackage con la opción `--java-options` para incluir Java
- O indica al usuario que instale Java 17+ antes de ejecutar
