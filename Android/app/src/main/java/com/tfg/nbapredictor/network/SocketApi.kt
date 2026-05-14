package com.tfg.nbapredictor.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tfg.nbapredictor.model.*
import com.tfg.nbapredictor.util.ServerConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.time.LocalDateTime
import java.util.UUID

/**
 * Cliente TCP por sockets para hablar con el backend (JSON + frames length-prefixed).
 */
object SocketApi {
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    private data class SocketResponse(
        val requestId: String? = null,
        val ok: Boolean = false,
        val data: JsonElement? = null,
        val error: String? = null
    )

    private suspend fun <T> call(action: String, payload: Any?, clazz: Class<T>): T =
        withContext(Dispatchers.IO) {
            val requestId = UUID.randomUUID().toString()
            val req = JsonObject().apply {
                addProperty("requestId", requestId)
                addProperty("action", action)
                add("payload", gson.toJsonTree(payload ?: emptyMap<String, Any>()))
            }

            val host = ServerConfig.getServerHost()
            val port = ServerConfig.getServerPort()

            Socket(host, port).use { socket ->
                val out = DataOutputStream(socket.getOutputStream())
                val input = DataInputStream(socket.getInputStream())

                SocketProtocol.writeFrame(out, gson.toJson(req))
                val respJson = SocketProtocol.readFrame(input)
                val resp = gson.fromJson(respJson, SocketResponse::class.java)

                if (!resp.ok) {
                    throw RuntimeException(resp.error ?: "Error desconocido")
                }

                val data = resp.data
                    ?: throw RuntimeException("Respuesta sin data")

                gson.fromJson(data, clazz)
            }
        }

    // ===== Usuarios =====
    suspend fun login(username: String, password: String): User =
        call("user.login", mapOf("username" to username, "password" to password), User::class.java)

    suspend fun register(user: User): User =
        call("user.register", user, User::class.java)

    suspend fun getUserById(id: Long): User =
        call("user.get", mapOf("id" to id), User::class.java)

    suspend fun getAllUsers(): Array<User> =
        call("user.list", emptyMap<String, Any>(), Array<User>::class.java)

    suspend fun updateUser(user: User): User =
        call("user.update", user, User::class.java)

    // ===== Partidos =====
    suspend fun getPartidos(): Array<Partido> =
        call("match.list", emptyMap<String, Any>(), Array<Partido>::class.java)

    suspend fun finalizarPartido(id: Long, puntosLocal: Int, puntosVisitante: Int): Partido =
        call("match.finalize", mapOf("id" to id, "puntosLocal" to puntosLocal, "puntosVisitante" to puntosVisitante), Partido::class.java)

    // ===== Apuestas =====
    suspend fun createApuesta(apuesta: Apuesta): Apuesta =
        call("bet.create", apuesta, Apuesta::class.java)

    suspend fun getApuestasByUsuario(id: Long): Array<Apuesta> =
        call("bet.byUser", mapOf("userId" to id), Array<Apuesta>::class.java)

    // ===== Equipos / Estadísticas =====
    suspend fun getEquipos(): Array<Equipo> =
        call("team.list", emptyMap<String, Any>(), Array<Equipo>::class.java)

    suspend fun getEquipoEstadisticas(equipoId: Long): EquipoEstadisticas =
        call("team.stats", mapOf("equipoId" to equipoId), EquipoEstadisticas::class.java)

    // ===== Tienda =====
    suspend fun canjearPuntos(request: CanjearPuntosRequest): CanjearPuntosResponse =
        call("store.redeem", request, CanjearPuntosResponse::class.java)
}

