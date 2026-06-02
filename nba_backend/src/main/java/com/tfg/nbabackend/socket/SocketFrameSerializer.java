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

    /**
     * Si los 4 bytes leídos como big-endian parecen "POST", "GET ", "HTTP", etc., el cliente
     * está hablando HTTP en el puerto del socket (tramas int32+JSON), no el protocolo TFG.
     */
    private static String describeIfLooksLikeHttp(int len) {
        byte[] b = new byte[]{
                (byte) (len >>> 24),
                (byte) (len >>> 16),
                (byte) (len >>> 8),
                (byte) len
        };
        String s = new String(b, StandardCharsets.US_ASCII);
        boolean printable = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 32 || c > 126) {
                printable = false;
                break;
            }
        }
        if (!printable) {
            return "";
        }
        return " — los 4 primeros bytes parecen ASCII \"" + s
                + "\": suele ser una petición HTTP (p. ej. Retrofit/OkHttp o navegador) "
                + "contra el puerto del socket; ese puerto solo acepta int32 longitud + JSON UTF-8.";
    }

    static String readFrame(DataInputStream in) throws IOException {
        try {
            int len = in.readInt();
            if (len < 0 || len > 50_000_000) {
                throw new IOException("Longitud de frame inválida: " + len + describeIfLooksLikeHttp(len));
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
