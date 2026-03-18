package com.tfg.nbapredictor.network

import com.tfg.nbapredictor.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * DTO para solicitud de login.
 * 
 * El backend solo requiere username y password para autenticación.
 */
data class LoginRequest(val username: String, val password: String)

/**
 * Respuesta de error del backend.
 * 
 * Formato: {"error": "mensaje"}
 */
data class ApiError(val error: String?)

/**
 * Interfaz que define todos los endpoints de la API REST del servidor.
 * 
 * <p>Esta interfaz es utilizada por Retrofit para generar la implementación
 * del cliente HTTP. Todos los métodos son suspend functions para trabajar
 * con coroutines de Kotlin.
 * 
 * <p>Los endpoints están organizados por categorías:
 * <ul>
 *   <li>Usuarios: registro, login, consulta y actualización</li>
 *   <li>Partidos: listado, creación y finalización</li>
 *   <li>Apuestas: creación y consulta</li>
 *   <li>Equipos: listado, consulta y estadísticas</li>
 *   <li>Jugadores: listado y consulta por equipo</li>
 *   <li>Tienda: canje de puntos por dinero</li>
 * </ul>
 * 
 * @author TFG
 * @version 1.0
 */
interface ApiService {
    
    // Usuarios
    @POST("usuarios/register")
    suspend fun register(@Body user: User): Response<User>
    
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<User>
    
    @GET("usuarios")
    suspend fun getAllUsers(): Response<List<User>>
    
    @GET("usuarios/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>
    
    @PUT("usuarios/{id}")
    suspend fun updateUser(@Path("id") id: Long, @Body user: User): Response<User>
    
    @PUT("usuarios/{id}/password")
    suspend fun changePassword(@Path("id") id: Long, @Body request: PasswordRequest): Response<ResponseBody>
    
    // Partidos
    @GET("partidos")
    suspend fun getPartidos(): Response<List<Partido>>
    
    @GET("partidos/equipo/{equipoId}")
    suspend fun getPartidosByEquipo(@Path("equipoId") equipoId: Long): Response<List<Partido>>
    
    @POST("partidos")
    suspend fun createPartido(@Body partido: Partido): Response<Partido>
    
    @PUT("partidos/{id}/finalizar")
    suspend fun finalizarPartido(
        @Path("id") id: Long,
        @Query("puntosLocal") puntosLocal: Int,
        @Query("puntosVisitante") puntosVisitante: Int
    ): Response<Partido>
    
    // Apuestas
    @POST("apuestas")
    suspend fun createApuesta(@Body apuesta: Apuesta): Response<Apuesta>
    
    @GET("apuestas/usuario/{id}")
    suspend fun getApuestasByUsuario(@Path("id") id: Long): Response<List<Apuesta>>
    
    // Equipos
    @GET("equipos")
    suspend fun getEquipos(): Response<List<Equipo>>
    
    @GET("equipos-con-estadisticas")
    suspend fun getEquiposConEstadisticas(): Response<List<Equipo>>
    
    @GET("equipos/{id}")
    suspend fun getEquipoById(@Path("id") id: Long): Response<Equipo>
    
    @GET("equipos/{id}/estadisticas")
    suspend fun getEquipoEstadisticas(@Path("id") id: Long): Response<EquipoEstadisticas>
    
    // Jugadores
    @GET("jugadores")
    suspend fun getJugadores(): Response<List<Jugador>>
    
    @GET("jugadores/equipo/{equipoId}")
    suspend fun getJugadoresByEquipo(@Path("equipoId") equipoId: Long): Response<List<Jugador>>
    
    // Tienda - Canjear puntos por dinero (PayPal simulado)
    /**
     * Canjea puntos virtuales por dinero mediante transferencia PayPal.
     * 
     * @param request solicitud con ID de usuario, puntos a canjear y email de PayPal
     * @return respuesta con el resultado del canje
     */
    @POST("tienda/canjear")
    suspend fun canjearPuntos(@Body request: CanjearPuntosRequest): Response<CanjearPuntosResponse>
}

/**
 * DTO para solicitud de cambio de contraseña.
 * 
 * @param password la nueva contraseña
 */
data class PasswordRequest(val password: String)

/**
 * DTO para solicitud de canje de puntos.
 * 
 * @param usuarioId ID del usuario que canjea los puntos
 * @param puntos cantidad de puntos a canjear (mínimo 1000)
 * @param emailPayPal email de PayPal del destinatario
 */
data class CanjearPuntosRequest(
    val usuarioId: Long,
    val puntos: Int,
    val emailPayPal: String
)

/**
 * DTO para respuesta de canje de puntos.
 * 
 * @param exito indica si el canje fue exitoso
 * @param mensaje mensaje descriptivo del resultado
 * @param eurosTransferidos cantidad en euros transferida
 * @param puntosCanjeados cantidad de puntos canjeados
 */
data class CanjearPuntosResponse(
    val exito: Boolean,
    val mensaje: String,
    val eurosTransferidos: Double,
    val puntosCanjeados: Int
)
