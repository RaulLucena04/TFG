# Documentación del protocolo de sockets

- **`PROTOCOLO_COMUNICACION_SOCKETS.md`** — especificación completa (Markdown).
- **`PROTOCOLO_COMUNICACION_SOCKETS.html`** — misma información para **Imprimir → Guardar como PDF** desde el navegador.
- **`FLUJO_APLICACION_DETALLADO.md`** — recorrido de los datos por capas (UI → socket → servicios → BD) y ejemplos (login, partidos, apuestas, finalizar partido).

## Generar PDF

1. Abre el `.html` en Chrome/Edge → `Ctrl+P` → destino **Guardar como PDF**.
2. O instala [Pandoc](https://pandoc.org/) y ejecuta desde esta carpeta:
   ```bash
   pandoc PROTOCOLO_COMUNICACION_SOCKETS.md -o PROTOCOLO_COMUNICACION_SOCKETS.pdf
   ```

## Código relacionado

| Concepto | Clase Java (backend) |
|----------|----------------------|
| Aceptar conexiones y apagar | `SocketServerRunner` |
| Bucle por cliente | `SocketClientHandler` |
| Acciones (`team.list`, etc.) | `SocketDispatcher` |
| Tramas length-prefixed | `SocketFrameSerializer` |
