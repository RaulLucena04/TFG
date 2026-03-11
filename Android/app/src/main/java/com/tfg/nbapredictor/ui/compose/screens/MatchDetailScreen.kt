package com.tfg.nbapredictor.ui.compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Partido

@Composable
fun MatchDetailScreen(
    partido: Partido? = null,
    onBack: () -> Unit
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        TextButton(onClick = onBack) {
            Text("← Volver")
        }
        partido?.let { p ->
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp)) {
                    val local = p.equipoLocal?.nombre ?: "Local"
                    val visitante = p.equipoVisitante?.nombre ?: "Visitante"
                    Text("$local vs $visitante", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    p.fecha?.let {
                        Text(it.toString())
                    }
                    Text("Estado: ${p.estado ?: "PROGRAMADO"}")
                    if (p.isFinalizado()) {
                        Text("${p.puntosLocal ?: 0} - ${p.puntosVisitante ?: 0}")
                    } else {
                        Button(onClick = { showCreateDialog = true }) {
                            Text("Crear apuesta")
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog && partido != null) {
        CreateBetDialog(
            open = true,
            preselectedPartido = partido,
            onDismiss = { showCreateDialog = false },
            onBetCreated = { showCreateDialog = false }
        )
    }
}

