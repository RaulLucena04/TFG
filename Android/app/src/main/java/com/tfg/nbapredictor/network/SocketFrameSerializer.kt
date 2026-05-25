package com.tfg.nbapredictor.network

import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.charset.StandardCharsets

/**
 * Serialización de tramas TCP: `int` big-endian (longitud en bytes UTF-8) + cuerpo JSON en UTF-8.
 *
 * El protocolo de aplicación (acciones `user.login`, `team.list`, etc.) lo define el backend
 * en la clase `SocketDispatcher`; esta clase solo empaqueta bytes, igual que `SocketFrameSerializer` en Java.
 */
internal object SocketFrameSerializer {
    fun writeFrame(out: DataOutputStream, json: String) {
        val bytes = json.toByteArray(StandardCharsets.UTF_8)
        out.writeInt(bytes.size)
        out.write(bytes)
        out.flush()
    }

    fun readFrame(input: DataInputStream): String {
        val len = input.readInt()
        require(len in 0..50_000_000) { "Longitud de frame inválida: $len" }
        val buf = ByteArray(len)
        input.readFully(buf)
        return String(buf, StandardCharsets.UTF_8)
    }
}
