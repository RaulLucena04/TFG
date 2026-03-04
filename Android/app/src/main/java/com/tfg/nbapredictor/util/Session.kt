package com.tfg.nbapredictor.util

import com.tfg.nbapredictor.model.User

object Session {
    private var currentUser: User? = null
    
    fun setCurrentUser(user: User) {
        currentUser = user
    }
    
    fun getCurrentUser(): User? = currentUser
    
    fun clearSession() {
        currentUser = null
    }
    
    fun isLoggedIn(): Boolean = currentUser != null
    
    fun isAdmin(): Boolean = currentUser?.isAdmin() ?: false
}
