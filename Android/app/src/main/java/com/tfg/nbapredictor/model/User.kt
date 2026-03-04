package com.tfg.nbapredictor.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long? = null,
    val username: String,
    val password: String? = null,
    @SerializedName("puntos")
    val points: Int = 1000,
    val rol: String? = "USER",
    val email: String
) {
    fun isAdmin(): Boolean = "ADMIN".equals(rol, ignoreCase = true)
}
