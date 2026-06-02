package com.tfg.nbapredictor.network

/**
 * DTO alineado con el backend (`CanjearPuntosRequest`) para la acción `store.redeem` por socket.
 */
data class CanjearPuntosRequest(
    val usuarioId: Long?,
    val puntos: Int,
    val emailPayPal: String
)

/**
 * DTO alineado con el backend (`CanjearPuntosResponse`).
 */
data class CanjearPuntosResponse(
    val exito: Boolean = false,
    val mensaje: String? = null,
    val eurosTransferidos: Double = 0.0,
    val puntosCanjeados: Int = 0
)
