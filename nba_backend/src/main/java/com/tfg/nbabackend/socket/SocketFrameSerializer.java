package com.tfg.nbabackend.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Serialización de tramas TCP: prefijo de longitud + cuerpo UTF-8 (JSON).
 *
 * <p><b>No es el protocolo de aplicación</b> (acciones tipo {@code team.list}); solo define cómo se
 * empaquetan bytes en el cable. El contrato de negocio vive en {@link SocketDispatcher}.</p>
 *
 * <h2>¿Por qué una trama con longitud?</h2>
 * <p>TCP es un flujo continuo; sin longitud explícita no se puede saber dónde termina un mensaje.</p>
 *
 * <h2>Formato</h2>
 * <ul>
 *   <li>{@code int32} big-endian: número de bytes UTF-8 del siguiente bloque</li>
 *   <li>Payload: bytes UTF-8 (típicamente un JSON)</li>
 * </ul>
 */
final class SocketFrameSerializer {
    private SocketFrameSerializer() {}

    static String readFrame(DataInputStream in) throws IOException {
        try {
            int len = in.readInt();
            if (len < 0 || len > 50_000_000) {
                throw new IOException("Longitud de frame inválida: " + len);
            }
            byte[] buf = new byte[len];
            in.readFully(buf);
            return new String(buf, StandardCharsets.UTF_8);
        } catch (EOFException eof) {
            return null;
        }
    }

    static void writeFrame(DataOutputStream out, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
        out.flush();
    }
}
