package com.tfg.nbapredictor.ui.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.network.SocketApi

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RankingsScreen() {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var loadError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loadError = null
        try {
            users = SocketApi.getAllUsers().toList().sortedByDescending { u -> u.points }
        } catch (e: Exception) {
            Log.e("RankingsScreen", "getAllUsers", e)
            loadError = e.message ?: e.toString()
        }
    }

    LazyColumn(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Text("Ranking", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            loadError?.let { err ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "No se pudo cargar el ranking.\n\n$err",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(Modifier.height(12.dp))
            }
        }
        itemsIndexed(users) { index, user ->
            RankingItem(rank = index + 1, user = user)
        }
    }
}

@Composable
private fun RankingItem(rank: Int, user: User) {
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("#$rank ${user.username}")
            Text("${user.points} pts")
        }
    }
}
