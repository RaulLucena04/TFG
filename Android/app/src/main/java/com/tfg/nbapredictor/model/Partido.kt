package com.tfg.nbapredictor.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Partido(
    val id: Long? = null,
    val fecha: LocalDateTime? = null,
    @SerializedName("equipoLocal")
    val equipoLocal: Equipo? = null,
    @SerializedName("equipoVisitante")
    val equipoVisitante: Equipo? = null,
    @SerializedName("puntosLocal")
    val puntosLocal: Int? = null,
    @SerializedName("puntosVisitante")
    val puntosVisitante: Int? = null,
    val estado: String? = "PROGRAMADO"
) {
    fun getRival(equipoId: Long): String {
        return when {
            equipoLocal?.id == equipoId -> equipoVisitante?.nombre ?: "Desconocido"
            equipoVisitante?.id == equipoId -> equipoLocal?.nombre ?: "Desconocido"
            else -> "Desconocido"
        }
    }
    
    fun getResultado(equipoId: Long): String {
        return when {
            equipoLocal?.id == equipoId -> "${puntosLocal ?: 0} - ${puntosVisitante ?: 0}"
            equipoVisitante?.id == equipoId -> "${puntosVisitante ?: 0} - ${puntosLocal ?: 0}"
            else -> "N/A"
        }
    }
    
    fun isProgramado(): Boolean = "PROGRAMADO".equals(estado, ignoreCase = true)
    fun isFinalizado(): Boolean = "FINALIZADO".equals(estado, ignoreCase = true)
}
