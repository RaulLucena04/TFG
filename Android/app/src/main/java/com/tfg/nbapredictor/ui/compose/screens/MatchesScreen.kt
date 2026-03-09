package com.tfg.nbapredictor.ui.compose.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.ui.compose.components.MatchItem
import com.tfg.nbapredictor.model.Partido
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    onMatchClick: (Partido) -> Unit = {}
) {
    var partidos by remember { mutableStateOf<List<Partido>>(emptyList()) }
    var filter by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        try {
            com.tfg.nbapredictor.network.RetrofitClient.apiService.getPartidos().body()?.let {
                partidos = it
            }
        } catch (_: Exception) { }
    }

    val filtered = when (filter) {
        "Próximos" -> partidos.filter { it.isProgramado() }
        "En curso" -> partidos.filter { "EN_CURSO".equals(it.estado, ignoreCase = true) }
        "Finalizados" -> partidos.filter { it.isFinalizado() }
        else -> partidos
    }

    Column(Modifier.padding(16.dp)) {
        Text("Partidos", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        FilterChips(
            options = listOf("Todos", "Próximos", "En curso", "Finalizados"),
            selected = filter,
            onSelected = { filter = it }
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered) { partido ->
                MatchItem(partido = partido, onClick = { onMatchClick(partido) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { opt ->
            FilterChip(
                selected = selected == opt,
                onClick = { onSelected(opt) },
                label = { Text(opt) }
            )
        }
    }
}
