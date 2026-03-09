package com.tfg.nbapredictor.util

import com.tfg.nbapredictor.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object Session {
    private var currentUser: User? = null

    private val _userUpdated = MutableSharedFlow<Unit>(replay = 0)
    val userUpdated: SharedFlow<Unit> = _userUpdated

    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun notifyUserUpdated() {
        _userUpdated.tryEmit(Unit)
    }
    
    fun getCurrentUser(): User? = currentUser
    
    fun clearSession() {
        currentUser = null
    }
    
    fun isLoggedIn(): Boolean = currentUser != null
    
    fun isAdmin(): Boolean = currentUser?.isAdmin() ?: false
}
