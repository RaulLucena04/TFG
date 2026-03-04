package com.tfg.nbapredictor.model

data class Jugador(
    val id: Long? = null,
    val nombre: String,
    val posicion: String,
    val promedioPuntos: Double = 0.0,
    val promedioAsistencias: Double = 0.0,
    val promedioRebotes: Double = 0.0,
    val equipo: Equipo? = null
)
