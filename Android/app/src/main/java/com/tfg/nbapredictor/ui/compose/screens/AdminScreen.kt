package com.tfg.nbapredictor.ui.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminScreen() {
    val scope = rememberCoroutineScope()

    var totalUsers by remember { mutableStateOf(0) }
    var activeMatches by remember { mutableStateOf(0) }
    var totalTeams by remember { mutableStateOf(0) }
    var pendingMatches by remember { mutableStateOf<List<Partido>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    var matchToFinalize by remember { mutableStateOf<Partido?>(null) }

    fun loadData() {
        scope.launch {
            loading = true
            error = null
            try {
                val usersRes = RetrofitClient.apiService.getAllUsers()
                val partidosRes = RetrofitClient.apiService.getPartidos()
                val equiposRes = RetrofitClient.apiService.getEquipos()

                val users = usersRes.body() ?: emptyList()
                val partidos = partidosRes.body() ?: emptyList()
                val equipos = equiposRes.body() ?: emptyList()

                totalUsers = users.size
                val activos = partidos.count { p ->
                    val e = p.estado?.uppercase() ?: ""
                    e == "PROGRAMADO" || e == "EN_CURSO" || e == "EN CURSO"
                }
                activeMatches = activos
                totalTeams = equipos.size

                pendingMatches = partidos.filter { p ->
                    val e = p.estado?.uppercase() ?: ""
                    e == "PROGRAMADO" || e.isEmpty()
                }.take(20)
            } catch (e: Exception) {
                error = "Error: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Panel de Administración",
            style = MaterialTheme.typography.headlineMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AdminStatCard(title = "Usuarios", value = totalUsers.toString(), modifier = Modifier.weight(1f))
            AdminStatCard(title = "Partidos activos", value = activeMatches.toString(), modifier = Modifier.weight(1f))
            AdminStatCard(title = "Apuestas", value = "N/A", modifier = Modifier.weight(1f))
            AdminStatCard(title = "Equipos", value = totalTeams.toString(), modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Partidos pendientes", style = MaterialTheme.typography.titleMedium)
            Button(onClick = { loadData() }) {
                Text("Actualizar datos")
            }
        }

        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pendingMatches) { partido ->
                AdminMatchItem(
                    partido = partido,
                    onFinalizeClick = { matchToFinalize = partido }
                )
            }
        }
    }

    val partidoDialog = matchToFinalize
    if (partidoDialog != null) {
        FinalizeMatchDialogCompose(
            partido = partidoDialog,
            onDismiss = { matchToFinalize = null },
            onFinalized = {
                matchToFinalize = null
                loadData()
            }
        )
    }
}

@Composable
private fun AdminStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, style = MaterialTheme.typography.bodySmall)
            Text(value, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AdminMatchItem(
    partido: Partido,
    onFinalizeClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            val local = partido.equipoLocal?.nombre ?: "Local"
            val visitante = partido.equipoVisitante?.nombre ?: "Visitante"
            Text("$local vs $visitante", style = MaterialTheme.typography.titleSmall)
            partido.fecha?.let {
                val fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                Text(
                    "${it.format(fmt)} · ${partido.estado ?: "Programado"}",
                    style = MaterialTheme.typography.bodySmall
                )
            } ?: run {
                Text(partido.estado ?: "Programado", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = onFinalizeClick) {
                Text("Finalizar")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FinalizeMatchDialogCompose(
    partido: Partido,
    onDismiss: () -> Unit,
    onFinalized: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var puntosLocalText by remember { mutableStateOf("") }
    var puntosVisitanteText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    val local = partido.equipoLocal?.nombre ?: "Local"
    val visitante = partido.equipoVisitante?.nombre ?: "Visitante"

    AlertDialog(
        onDismissRequest = { if (!loading) onDismiss() },
        title = { Text("Finalizar partido") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("$local vs $visitante", style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(
                    value = puntosLocalText,
                    onValueChange = { puntosLocalText = it.filter { c -> c.isDigit() } },
                    label = { Text("Puntos $local") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = puntosVisitanteText,
                    onValueChange = { puntosVisitanteText = it.filter { c -> c.isDigit() } },
                    label = { Text("Puntos $visitante") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    error = null
                    val puntosLocal = puntosLocalText.toIntOrNull()
                    val puntosVisitante = puntosVisitanteText.toIntOrNull()
                    if (puntosLocal == null || puntosLocal < 0) {
                        error = "Ingresa puntos válidos para el equipo local"
                        return@Button
                    }
                    if (puntosVisitante == null || puntosVisitante < 0) {
                        error = "Ingresa puntos válidos para el equipo visitante"
                        return@Button
                    }
                    val id = partido.id ?: return@Button
                    scope.launch {
                        loading = true
                        try {
                            val response = RetrofitClient.apiService.finalizarPartido(id, puntosLocal, puntosVisitante)
                            if (response.isSuccessful) {
                                onFinalized()
                                onDismiss()
                            } else {
                                error = "Error al finalizar: ${response.code()}"
                            }
                        } catch (e: Exception) {
                            error = "Error: ${e.message}"
                        } finally {
                            loading = false
                        }
                    }
                }
            ) {
                Text("Finalizar")
            }
        },
        dismissButton = {
            TextButton(onClick = { if (!loading) onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}

