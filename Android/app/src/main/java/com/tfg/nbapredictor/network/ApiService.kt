package com.tfg.nbapredictor.network

import com.tfg.nbapredictor.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/** DTO para login: el backend solo requiere username y password */
data class LoginRequest(val username: String, val password: String)

/** Respuesta de error del backend: {"error": "mensaje"} */
data class ApiError(val error: String?)

interface ApiService {
    
    // Usuarios
    @POST("usuarios/register")
    suspend fun register(@Body user: User): Response<User>
    
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<User>
    
    @GET("usuarios")
    suspend fun getAllUsers(): Response<List<User>>
    
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
    
    @GET("equipos/{id}")
    suspend fun getEquipoById(@Path("id") id: Long): Response<Equipo>
    
    // Jugadores
    @GET("jugadores")
    suspend fun getJugadores(): Response<List<Jugador>>
    
    @GET("jugadores/equipo/{equipoId}")
    suspend fun getJugadoresByEquipo(@Path("equipoId") equipoId: Long): Response<List<Jugador>>
}

data class PasswordRequest(val password: String)
