package com.tfg.nbabackend.socket;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Mensaje de petición del protocolo por sockets.
 *
 * <p>Formato esperado:
 * <ul>
 *   <li>{@code requestId}: correlación request/response</li>
 *   <li>{@code action}: operación (por ejemplo, {@code match.list})</li>
 *   <li>{@code payload}: parámetros/DTO como JSON</li>
 * </ul>
 * </p>
 *
 * <h2>Ejemplo</h2>
 *
 * <pre>
 * {
 *   "requestId": "6c1b5f3f-0a4d-4c4a-9d4d-6e2f1b9e2a10",
 *   "action": "match.finalize",
 *   "payload": { "id": 12, "puntosLocal": 101, "puntosVisitante": 98 }
 * }
 * </pre>
 *
 * <p>Este JSON se envía dentro de un frame definido por {@link SocketProtocol}.</p>
 */
public class SocketRequest {
    public String requestId;
    public String action;
    public JsonNode payload;
}

