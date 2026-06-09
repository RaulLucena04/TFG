package com.tfg.nbapredictor.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class User(
    val id: Long? = null,
    val username: String,
    val password: String? = null,
    @JsonProperty("puntos")
    @SerializedName("puntos")
    val points: Int = 1000,
    val rol: String? = "USER",
    val email: String? = null  // Opcional: el login solo envía username/password; la respuesta incluye email
) {
    fun isAdmin(): Boolean = "ADMIN".equals(rol, ignoreCase = true)
}
