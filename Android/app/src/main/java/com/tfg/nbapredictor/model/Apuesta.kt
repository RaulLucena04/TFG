package com.tfg.nbapredictor.model

data class Apuesta(
    val id: Long? = null,
    val puntosApostados: Int,
    val prediccion: String, // "LOCAL" o "VISITANTE"
    val resultado: String? = "PENDIENTE", // "PENDIENTE", "GANADA", "PERDIDA"
    val cuota: Double? = null,
    val partido: Partido? = null,
    val usuario: User? = null
) {
    fun getGananciaPotencial(): Double {
        return if (cuota != null && cuota > 0) {
            puntosApostados * cuota
        } else {
            0.0
        }
    }
    
    fun isActiva(): Boolean = "PENDIENTE".equals(resultado, ignoreCase = true)
    fun isGanada(): Boolean = "GANADA".equals(resultado, ignoreCase = true)
    fun isPerdida(): Boolean = "PERDIDA".equals(resultado, ignoreCase = true)
}
