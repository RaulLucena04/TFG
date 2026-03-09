package com.tfg.nbapredictor.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.Partido
import java.time.format.DateTimeFormatter

@Composable
fun MatchItem(
    partido: Partido,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val local = partido.equipoLocal?.nombre ?: "Local"
            val visitante = partido.equipoVisitante?.nombre ?: "Visitante"
            Text("$local vs $visitante", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            partido.fecha?.let {
                Text(
                    it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
            val estado = partido.estado ?: "PROGRAMADO"
            Text("Estado: $estado", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
            if (partido.isFinalizado()) {
                Text(
                    "${partido.puntosLocal ?: 0} - ${partido.puntosVisitante ?: 0}",
                    style = androidx.compose.material3.MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
