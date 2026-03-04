package com.tfg.nbapredictor.model

data class Equipo(
    val id: Long? = null,
    val nombre: String,
    val conferencia: String? = null,
    val division: String? = null,
    val victorias: Int = 0,
    val derrotas: Int = 0,
    val ppg: Double = 0.0,
    val rpg: Double = 0.0,
    val apg: Double = 0.0,
    val jugadores: List<Jugador>? = null,
    val partidosRecientes: List<Partido>? = null
)
