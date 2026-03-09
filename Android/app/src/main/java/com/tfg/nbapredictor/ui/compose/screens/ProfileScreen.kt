package com.tfg.nbapredictor.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen() {
    var user by remember { mutableStateOf(Session.getCurrentUser()) }
    var apuestas by remember { mutableStateOf<List<Apuesta>>(emptyList()) }
    var ranking by remember { mutableStateOf(-1) }

    suspend fun loadProfile() {
        val u = Session.getCurrentUser() ?: return
        u.id ?: return
        try {
            RetrofitClient.apiService.getUserById(u.id).body()?.let {
                Session.setCurrentUser(it)
                user = it
            }
            RetrofitClient.apiService.getApuestasByUsuario(u.id).body()?.let {
                apuestas = it
            }
            RetrofitClient.apiService.getAllUsers().body()?.let { users ->
                val pos = users.sortedByDescending { it.points }.indexOfFirst { it.id == user?.id }
                ranking = if (pos >= 0) pos + 1 else -1
            }
        } catch (_: Exception) { }
    }

    LaunchedEffect(Unit) {
        loadProfile()
    }
    LaunchedEffect(Unit) {
        Session.userUpdated.collectLatest { loadProfile() }
    }

    val u = user ?: return
    val ganadas = apuestas.count { it.isGanada() }
    val finalizadas = apuestas.count { it.isGanada() || it.isPerdida() }
    val winRate = if (finalizadas > 0) String.format("%.1f%%", ganadas * 100.0 / finalizadas) else "0%"

    LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("Perfil", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(u.username, style = MaterialTheme.typography.titleLarge)
                    Text(u.email ?: "-", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(Modifier.weight(1f)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${u.points}", style = MaterialTheme.typography.titleMedium)
                        Text("Puntos")
                    }
                }
                Card(Modifier.weight(1f)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${apuestas.size}", style = MaterialTheme.typography.titleMedium)
                        Text("Apuestas")
                    }
                }
                Card(Modifier.weight(1f)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(winRate, style = MaterialTheme.typography.titleMedium)
                        Text("Tasa")
                    }
                }
                Card(Modifier.weight(1f)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(if (ranking > 0) "#$ranking" else "-", style = MaterialTheme.typography.titleMedium)
                        Text("Ranking")
                    }
                }
            }
        }
        item {
            Text("Apuestas recientes", style = MaterialTheme.typography.titleMedium)
        }
        items(apuestas.take(10)) { apuesta ->
            BetCardMini(apuesta)
        }
    }
}

@Composable
private fun BetCardMini(apuesta: Apuesta) {
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(Modifier.padding(12.dp)) {
            Text("${apuesta.prediccion} · ${apuesta.puntosApostados} pts")
            Spacer(Modifier.weight(1f))
            Text(apuesta.resultado ?: "-")
        }
    }
}
