package com.tfg.nbapredictor.network

import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.model.Equipo
import com.tfg.nbapredictor.model.EquipoEstadisticas
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.util.ServerConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.ArrayList
import java.util.UUID

/**
 * Cliente TCP por sockets para hablar con el backend (JSON + frames length-prefixed).
 *
 * Las respuestas se interpretan con **Jackson** (JavaTime + Kotlin), alineado con el `ObjectMapper`
 * del servidor. Así se evitan fallos del adaptador Kotlin de **Gson** (p. ej.
 * `Array must have size 1, but has size 5`) al deserializar listas de `Partido` / `Apuesta`.
 */
object SocketApi {

    private val mapper: ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        registerModule(KotlinModule.Builder().build())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    private suspend fun exchange(action: String, payload: Any?): JsonNode =
        withContext(Dispatchers.IO) {
            val requestId = UUID.randomUUID().toString()
            val req = mapper.createObjectNode().apply {
                put("requestId", requestId)
                put("action", action)
                set<JsonNode>("payload", mapper.valueToTree(payload ?: emptyMap<String, Any>()))
            }

            val host = ServerConfig.getServerHost()
            val port = ServerConfig.getServerPort()

            Socket(host, port).use { socket ->
                val out = DataOutputStream(socket.getOutputStream())
                val input = DataInputStream(socket.getInputStream())

                SocketFrameSerializer.writeFrame(out, mapper.writeValueAsString(req))
                val respJson = SocketFrameSerializer.readFrame(input)
                val root = mapper.readTree(respJson)

                if (!root.path("ok").asBoolean()) {
                    val err = root.path("error").asText(null)
                    throw RuntimeException(err?.takeIf { it.isNotBlank() } ?: "Error desconocido")
                }

                root.get("data") ?: throw RuntimeException("Respuesta sin data")
            }
        }

    private suspend fun <T> convert(action: String, payload: Any?, javaType: JavaType): T =
        try {
            val data = exchange(action, payload)
            mapper.convertValue(data, javaType)
        } catch (e: Exception) {
            Log.e("SocketApi", "Fallo parseando respuesta (acción=$action)", e)
            throw RuntimeException(
                "No se pudo interpretar la respuesta del servidor (${e.javaClass.simpleName}: ${e.message}). " +
                    "Si acabas de actualizar solo el backend o solo la app, alinea ambas versiones.",
                e
            )
        }

    private fun <T> typeOf(clazz: Class<T>): JavaType = mapper.typeFactory.constructType(clazz)

    private fun <E> listTypeOf(element: Class<E>): JavaType =
        mapper.typeFactory.constructCollectionType(ArrayList::class.java, element)

    // ===== Usuarios =====
    suspend fun login(username: String, password: String): User =
        convert("user.login", mapOf("username" to username, "password" to password), typeOf(User::class.java))

    suspend fun register(user: User): User =
        convert("user.register", user, typeOf(User::class.java))

    suspend fun getUserById(id: Long): User =
        convert("user.get", mapOf("id" to id), typeOf(User::class.java))

    suspend fun getAllUsers(): List<User> =
        convert("user.list", emptyMap<String, Any>(), listTypeOf(User::class.java))

    suspend fun updateUser(user: User): User =
        convert("user.update", user, typeOf(User::class.java))

    // ===== Partidos =====
    suspend fun getPartidos(): List<Partido> =
        convert("match.list", emptyMap<String, Any>(), listTypeOf(Partido::class.java))

    suspend fun finalizarPartido(id: Long, puntosLocal: Int, puntosVisitante: Int): Partido =
        convert(
            "match.finalize",
            mapOf("id" to id, "puntosLocal" to puntosLocal, "puntosVisitante" to puntosVisitante),
            typeOf(Partido::class.java)
        )

    // ===== Apuestas =====
    suspend fun createApuesta(apuesta: Apuesta): Apuesta =
        convert("bet.create", apuesta, typeOf(Apuesta::class.java))

    suspend fun getApuestasByUsuario(id: Long): List<Apuesta> =
        convert("bet.byUser", mapOf("userId" to id), listTypeOf(Apuesta::class.java))

    // ===== Equipos / Estadísticas =====
    suspend fun getEquipos(): List<Equipo> =
        convert("team.list", emptyMap<String, Any>(), listTypeOf(Equipo::class.java))

    suspend fun getEquipoEstadisticas(equipoId: Long): EquipoEstadisticas =
        convert("team.stats", mapOf("equipoId" to equipoId), typeOf(EquipoEstadisticas::class.java))

    // ===== Tienda =====
    suspend fun canjearPuntos(request: CanjearPuntosRequest): CanjearPuntosResponse =
        convert("store.redeem", request, typeOf(CanjearPuntosResponse::class.java))
}
