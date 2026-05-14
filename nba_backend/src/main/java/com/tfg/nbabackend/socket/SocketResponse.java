package com.tfg.nbabackend.socket;

/**
 * Mensaje de respuesta del protocolo por sockets.
 *
 * <p>Si {@code ok} es {@code true}, la respuesta va en {@code data}. Si es {@code false}, se
 * rellena {@code error}.</p>
 *
 * <h2>Ejemplos</h2>
 *
 * <p>Respuesta OK:</p>
 * <pre>
 * { "requestId": "...", "ok": true, "data": { ... }, "error": null }
 * </pre>
 *
 * <p>Respuesta de error:</p>
 * <pre>
 * { "requestId": "...", "ok": false, "data": null, "error": "Acción no soportada: foo.bar" }
 * </pre>
 *
 * <p>El cliente debe usar {@code requestId} para correlacionar la respuesta con su petición si
 * está multiplexando varias en paralelo (en este proyecto normalmente no lo hace, pero queda soportado).</p>
 */
public class SocketResponse {
    public String requestId;
    public boolean ok;
    public Object data;
    public String error;

    public static SocketResponse ok(String requestId, Object data) {
        SocketResponse r = new SocketResponse();
        r.requestId = requestId;
        r.ok = true;
        r.data = data;
        return r;
    }

    public static SocketResponse error(String requestId, String message) {
        SocketResponse r = new SocketResponse();
        r.requestId = requestId;
        r.ok = false;
        r.error = message;
        return r;
    }
}

