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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBetDialog(
    open: Boolean,
    preselectedPartido: Partido? = null,
    onDismiss: () -> Unit,
    onBetCreated: () -> Unit
) {
    if (!open) return

    val scope = rememberCoroutineScope()

    var partidos by remember { mutableStateOf<List<Partido>>(emptyList()) }
    var selectedPartido by remember { mutableStateOf(preselectedPartido) }
    var selectedPrediccion by remember { mutableStateOf("LOCAL") }
    var puntosText by remember { mutableStateOf("") }
    var cuota by remember { mutableStateOf<Double?>(null) }
    var ganancia by remember { mutableStateOf<Double?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var loadingCuota by remember { mutableStateOf(false) }

    val user = Session.getCurrentUser()

    LaunchedEffect(preselectedPartido) {
        if (preselectedPartido != null) {
            partidos = listOf(preselectedPartido)
            selectedPartido = preselectedPartido
            recalculateCuotaAndGanancia(
                scope = scope,
                partido = preselectedPartido,
                prediccion = selectedPrediccion,
                puntosText = puntosText,
                setCuota = { cuota = it },
                setGanancia = { ganancia = it },
                setLoading = { loadingCuota = it }
            )
        } else {
            // Cargar partidos disponibles (PROGRAMADO o EN_CURSO)
            scope.launch {
                try {
                    val response = RetrofitClient.apiService.getPartidos()
                    if (response.isSuccessful) {
                        val todos = response.body() ?: emptyList()
                        partidos = todos.filter { p ->
                            val e = p.estado?.uppercase() ?: ""
                            e == "PROGRAMADO" || e == "EN_CURSO"
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    fun onChangeInputs() {
        val partido = selectedPartido
        if (partido == null) {
            cuota = null
            ganancia = null
            return
        }
        recalculateCuotaAndGanancia(
            scope = scope,
            partido = partido,
            prediccion = selectedPrediccion,
            puntosText = puntosText,
            setCuota = { cuota = it },
            setGanancia = { ganancia = it },
            setLoading = { loadingCuota = it }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear apuesta") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Puntos disponibles: ${user?.points ?: 0}",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Partido
                if (preselectedPartido != null) {
                    val local = preselectedPartido.equipoLocal?.nombre ?: "Local"
                    val visitante = preselectedPartido.equipoVisitante?.nombre ?: "Visitante"
                    Text("$local vs $visitante", style = MaterialTheme.typography.bodyMedium)
                } else {
                    OutlinedTextField(
                        value = selectedPartidoLabel(selectedPartido),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = false,
                        label = { Text("Partido") },
                        placeholder = { Text("Selecciona un partido en la lista") }
                    )
                    if (partidos.isEmpty()) {
                        Text("No hay partidos disponibles para apostar", color = MaterialTheme.colorScheme.error)
                    } else {
                        // Lista simple de partidos para elegir (primero seleccionado por defecto)
                        if (selectedPartido == null && partidos.isNotEmpty()) {
                            selectedPartido = partidos.first()
                            onChangeInputs()
                        }
                    }
                }

                // Predicción
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PredictionChip(
                        label = "LOCAL",
                        selected = selectedPrediccion == "LOCAL",
                        onClick = {
                            selectedPrediccion = "LOCAL"
                            onChangeInputs()
                        }
                    )
                    PredictionChip(
                        label = "VISITANTE",
                        selected = selectedPrediccion == "VISITANTE",
                        onClick = {
                            selectedPrediccion = "VISITANTE"
                            onChangeInputs()
                        }
                    )
                }

                // Puntos
                    OutlinedTextField(
                        value = puntosText,
                        onValueChange = {
                            puntosText = it.filter { c -> c.isDigit() }
                            onChangeInputs()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Puntos a apostar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                if (loadingCuota) {
                    Text("Calculando cuota...", style = MaterialTheme.typography.bodySmall)
                } else if (cuota != null) {
                    Text("Cuota: %.2f".format(cuota), style = MaterialTheme.typography.bodyMedium)
                }

                if (ganancia != null) {
                    Text(
                        "Ganancia potencial: %.0f puntos".format(ganancia),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    error = null
                    val u = Session.getCurrentUser()
                    if (u == null || u.id == null) {
                        error = "No hay usuario en sesión"
                        return@Button
                    }
                    val partido = selectedPartido
                    if (partido == null || partido.id == null) {
                        error = "Selecciona un partido válido"
                        return@Button
                    }
                    val prediccion = selectedPrediccion
                    val puntos = puntosText.toIntOrNull() ?: 0
                    if (puntos <= 0) {
                        error = "Los puntos deben ser mayores a 0"
                        return@Button
                    }
                    if (u.points < puntos) {
                        error = "No tienes suficientes puntos. Disponibles: ${u.points}"
                        return@Button
                    }

                    scope.launch {
                        try {
                            val cuotaFinal = cuota ?: calcularCuota(partido, prediccion)
                            val apuesta = Apuesta(
                                puntosApostados = puntos,
                                prediccion = prediccion,
                                cuota = cuotaFinal,
                                partido = Partido(id = partido.id),
                                usuario = u
                            )
                            val response = RetrofitClient.apiService.createApuesta(apuesta)
                            if (response.isSuccessful) {
                                u.id?.let { id ->
                                    RetrofitClient.apiService.getUserById(id).body()?.let { updated ->
                                        Session.setCurrentUser(updated)
                                        Session.notifyUserUpdated()
                                    }
                                }
                                onBetCreated()
                                onDismiss()
                            } else {
                                error = response.errorBody()?.string() ?: "Error al crear apuesta"
                            }
                        } catch (e: Exception) {
                            error = "Error: ${e.message}"
                        }
                    }
                }
            ) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PredictionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.material3.FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) }
    )
}

private fun selectedPartidoLabel(partido: Partido?): String {
    if (partido == null) return ""
    val local = partido.equipoLocal?.nombre ?: "Local"
    val visitante = partido.equipoVisitante?.nombre ?: "Visitante"
    return "$local vs $visitante"
}

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun calcularCuota(partido: Partido, prediccion: String): Double {
    val idLocal = partido.equipoLocal?.id ?: return 2.0
    val idVisitante = partido.equipoVisitante?.id ?: return 2.0
    return try {
        val statsLocal = RetrofitClient.apiService.getEquipoEstadisticas(idLocal).body()
        val statsVisitante = RetrofitClient.apiService.getEquipoEstadisticas(idVisitante).body()
        if (statsLocal == null || statsVisitante == null) return 2.0
        val totalLocal = statsLocal.victorias + statsLocal.derrotas
        val totalVisitante = statsVisitante.victorias + statsVisitante.derrotas
        val winRateLocal = if (totalLocal > 0) statsLocal.victorias.toDouble() / totalLocal else 0.5
        val winRateVisitante = if (totalVisitante > 0) statsVisitante.victorias.toDouble() / totalVisitante else 0.5
        val diferenciaRecord = winRateLocal - winRateVisitante
        val ventajaLocal = 0.05
        val cuotaBase = if (prediccion.equals("LOCAL", ignoreCase = true)) {
            2.0 + (-diferenciaRecord * 0.6 - ventajaLocal)
        } else {
            2.0 + (diferenciaRecord * 0.6 + ventajaLocal)
        }
        val cuota = cuotaBase.coerceIn(1.5, 5.0)
        (cuota * 100).toInt() / 100.0
    } catch (_: Exception) {
        2.0
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun recalculateCuotaAndGanancia(
    scope: kotlinx.coroutines.CoroutineScope,
    partido: Partido,
    prediccion: String,
    puntosText: String,
    setCuota: (Double?) -> Unit,
    setGanancia: (Double?) -> Unit,
    setLoading: (Boolean) -> Unit
) {
    scope.launch {
        setLoading(true)
        val cuota = calcularCuota(partido, prediccion)
        setCuota(cuota)
        val puntos = puntosText.toIntOrNull() ?: 0
        setGanancia(if (puntos > 0) puntos * cuota else null)
        setLoading(false)
    }
}

