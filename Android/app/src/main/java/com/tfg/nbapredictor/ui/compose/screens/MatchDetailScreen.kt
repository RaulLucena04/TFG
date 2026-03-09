package com.tfg.nbapredictor.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Partido

@Composable
fun MatchDetailScreen(
    partido: Partido? = null,
    onBack: () -> Unit
) {
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
                        Button(onClick = { /* Crear apuesta */ }) {
                            Text("Crear apuesta")
                        }
                    }
                }
            }
        }
    }
}
