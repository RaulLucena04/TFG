package com.tfg.nbapredictor.ui.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.SocketApi
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val points: Int = 0,
    val activeBets: Int = 0,
    val winRate: String = "0%",
    val upcomingMatches: List<Partido> = emptyList()
)

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            val user = Session.getCurrentUser() ?: return@launch
            try {
                val updated = SocketApi.getUserById(user.id!!)
                Session.setCurrentUser(updated)
                Session.notifyUserUpdated()
            } catch (_: Exception) { }

            val currentUser = Session.getCurrentUser() ?: return@launch
            _state.value = _state.value.copy(points = currentUser.points)

            try {
                val apuestas = SocketApi.getApuestasByUsuario(currentUser.id!!).toList()
                val activas = apuestas.count { it.isActiva() }
                val ganadas = apuestas.count { it.isGanada() }
                val total = apuestas.size
                _state.value = _state.value.copy(
                    activeBets = activas,
                    winRate = if (total > 0) "${(ganadas * 100 / total)}%" else "0%"
                )

                val partidos = SocketApi.getPartidos().toList().filter { it.isProgramado() }.take(5)
                _state.value = _state.value.copy(upcomingMatches = partidos)
            } catch (_: Exception) { }
        }
    }
}
