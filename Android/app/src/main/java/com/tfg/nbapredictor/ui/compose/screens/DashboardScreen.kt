package com.tfg.nbapredictor.ui.compose.screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tfg.nbapredictor.ui.compose.components.MatchItem
import com.tfg.nbapredictor.ui.compose.viewmodel.DashboardViewModel
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DashboardScreen(
    onMatchClick: (com.tfg.nbapredictor.model.Partido) -> Unit = {},
    viewModel: DashboardViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }
    LaunchedEffect(Unit) {
        Session.userUpdated.collectLatest {
            viewModel.loadData()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = state.points.toString(),
                    subtitle = "Puntos",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = state.activeBets.toString(),
                    subtitle = "Apuestas activas",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.winRate, style = MaterialTheme.typography.headlineSmall)
                    Text("Tasa de aciertos", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            Text(
                "Próximos partidos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(state.upcomingMatches) { partido ->
            MatchItem(
                partido = partido,
                onClick = { onMatchClick(partido) }
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.headlineSmall)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
