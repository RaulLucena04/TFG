package com.tfg.nbabackend.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utilidades del protocolo por sockets: frames JSON con prefijo de longitud.
 *
 * <h2>¿Por qué un "frame"?</h2>
 * <p>TCP es un stream: no conserva "mensajes". Si el cliente envía 2 JSON seguidos, el servidor podría
 * recibirlos juntos o cortados. Para poder leer exactamente 1 mensaje, añadimos un prefijo de longitud.</p>
 *
 * <h2>Formato del frame</h2>
 * <p>El frame se codifica como:
 * <ul>
 *   <li>{@code int32} big-endian con el número de bytes UTF-8</li>
 *   <li>payload UTF-8 JSON</li>
 * </ul>
 * </p>
 *
 * <h2>Límites y errores típicos</h2>
 * <ul>
 *   <li>Si la longitud es negativa o enorme, se considera frame corrupto y se corta con excepción.</li>
 *   <li>Si el cliente cierra la conexión, {@link #readFrame(DataInputStream)} devuelve {@code null}.</li>
 * </ul>
 */
final class SocketProtocol {
    private SocketProtocol() {}

    static String readFrame(DataInputStream in) throws IOException {
        try {
            // 1) Leer longitud (4 bytes). Si no hay más datos (EOF), el cliente cerró conexión.
            int len = in.readInt();
            if (len < 0 || len > 50_000_000) {
                throw new IOException("Longitud de frame inválida: " + len);
            }
            // 2) Leer exactamente "len" bytes del stream.
            byte[] buf = new byte[len];
            in.readFully(buf);
            // 3) Interpretarlos como UTF-8 (el JSON se manda en esa codificación).
            return new String(buf, StandardCharsets.UTF_8);
        } catch (EOFException eof) {
            return null;
        }
    }

    static void writeFrame(DataOutputStream out, String json) throws IOException {
        // Importante: usamos los bytes UTF-8 reales para que la longitud sea correcta.
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }
}

