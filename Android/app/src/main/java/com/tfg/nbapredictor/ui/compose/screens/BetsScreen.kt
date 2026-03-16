package com.tfg.nbapredictor.ui.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BetsScreen() {
    var apuestas by remember { mutableStateOf<List<Apuesta>>(emptyList()) }
    var points by remember { mutableStateOf(Session.getCurrentUser()?.points ?: 0) }
    var showCreateDialog by remember { mutableStateOf(false) }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loadBets() {
        val user = Session.getCurrentUser() ?: return
        user.id ?: return
        try {
            RetrofitClient.apiService.getUserById(user.id).body()?.let {
                Session.setCurrentUser(it)
                points = it.points
            }
            RetrofitClient.apiService.getApuestasByUsuario(user.id).body()?.let {
                apuestas = it
            }
        } catch (_: Exception) { }
    }

    LaunchedEffect(Unit) { loadBets() }
    LaunchedEffect(Unit) {
        Session.userUpdated.collectLatest { loadBets() }
    }

    val activas = apuestas.filter { it.isActiva() }
    val historial = apuestas.filter { it.isGanada() || it.isPerdida() }
    val ganadas = apuestas.count { it.isGanada() }
    val winRate = if (apuestas.isNotEmpty() && apuestas.any { it.isGanada() || it.isPerdida() }) {
        val fin = apuestas.count { it.isGanada() || it.isPerdida() }
        "${(ganadas * 100 / fin)}%"
    } else "0%"

    androidx.compose.material3.Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Crear apuesta")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier.padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("Apuestas", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("Puntos: $points")
                    Text("Total: ${apuestas.size}")
                    Text("Ganadas: $ganadas")
                    Text("Tasa: $winRate")
                }
                Spacer(Modifier.height(16.dp))
            }
            item { Text("Activas", style = MaterialTheme.typography.titleMedium) }
            items(activas) { a ->
                BetCard(a, showResultado = false)
            }
            item {
                Spacer(Modifier.height(16.dp))
                Text("Historial", style = MaterialTheme.typography.titleMedium)
            }
            items(historial) { apuesta ->
                BetCard(apuesta, showResultado = true)
            }
        }
    }

    if (showCreateDialog) {
        CreateBetDialog(
            open = true,
            preselectedPartido = null,
            onDismiss = { showCreateDialog = false },
            onBetCreated = { showCreateDialog = false }
        )
    }
}

@Composable
private fun BetCard(apuesta: Apuesta, showResultado: Boolean) {
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(Modifier.padding(16.dp)) {
            val partido = apuesta.partido
            val matchText = if (partido?.equipoLocal != null && partido.equipoVisitante != null) {
                "${partido.equipoLocal!!.nombre} vs ${partido.equipoVisitante!!.nombre}"
            } else "Partido"
            Text(matchText)
            Text("Predicción: ${apuesta.prediccion}")
            Text("Puntos: ${apuesta.puntosApostados} · Cuota: ${apuesta.cuota}")
            if (showResultado) {
                Text("Resultado: ${apuesta.resultado ?: "-"}")
            }
        }
    }
}
