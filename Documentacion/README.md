# DOCUMENTACIÓN DEL PROYECTO NBA PREDICTOR

Esta carpeta contiene la documentación completa del proyecto según los requisitos de entrega.

## ESTRUCTURA

```
Documentacion/
├── Analisis del proyecto/
│   └── Analisis del Proyecto.md
├── Manual de usuario/
│   └── Manual de Usuario.md
└── README.md (este archivo)
```

## CONTENIDO

### Análisis del Proyecto

Documento técnico que incluye:
- Introducción y propósito del proyecto
- Objetivos y viabilidad
- Análisis de requisitos
- Diseño de arquitectura
- Implementación
- Pruebas
- Conclusiones
- Bibliografía y glosario

### Manual de Usuario

Documento orientado al usuario final que incluye:
- Instrucciones de instalación para servidor, cliente de escritorio y Android
- Guía de uso detallada de todas las funcionalidades
- Configuración y solución de problemas

## CONVERSIÓN A PDF

Los documentos están en formato Markdown (.md). Para convertirlos a PDF, puedes usar:

### Opción 1: Pandoc (Recomendado)

1. Instala Pandoc: https://pandoc.org/installing.html
2. Ejecuta desde la terminal:

```bash
# Para Análisis del Proyecto
pandoc "Analisis del proyecto/Analisis del Proyecto.md" -o "Analisis del proyecto/Analisis del Proyecto.pdf" --pdf-engine=xelatex -V geometry:margin=2.5cm

# Para Manual de Usuario
pandoc "Manual de usuario/Manual de Usuario.md" -o "Manual de usuario/Manual de Usuario.pdf" --pdf-engine=xelatex -V geometry:margin=2.5cm
```

### Opción 2: Herramientas online

- **Markdown to PDF**: https://www.markdowntopdf.com/
- **Dillinger**: https://dillinger.io/ (exporta a PDF)
- **StackEdit**: https://stackedit.io/ (exporta a PDF)

### Opción 3: Visual Studio Code

1. Instala la extensión "Markdown PDF"
2. Abre el archivo .md
3. Presiona `Ctrl+Shift+P` (o `Cmd+Shift+P` en Mac)
4. Selecciona "Markdown PDF: Export (pdf)"

### Opción 4: GitHub

1. Sube los archivos a un repositorio de GitHub
2. GitHub renderiza automáticamente los archivos .md
3. Usa una herramienta de impresión a PDF desde el navegador

## NOTAS

- Los documentos están escritos en Markdown estándar
- Las secciones están numeradas según el formato requerido
- Se pueden añadir capturas de pantalla en las secciones correspondientes del Manual de Usuario
- Los diagramas UML y E/R mencionados en el Análisis deben generarse con herramientas CASE (PlantUML, Draw.io, MySQL Workbench, etc.)

## FORMATO DE ENTREGA

Según los requisitos, la entrega debe incluir:

- ✅ Análisis del Proyecto (PDF/HTML)
- ✅ Manual de Usuario (PDF/HTML)
- ✅ Código fuente de las aplicaciones
- ✅ Instalador de las aplicaciones
- ⚠️ Diagramas UML y E/R (deben generarse con herramientas apropiadas)

## CONTACTO

Para cualquier duda sobre la documentación, consulta el código fuente o la documentación técnica del proyecto.
