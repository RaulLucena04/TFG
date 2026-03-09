package com.tfg.nbapredictor.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.network.CanjearPuntosRequest
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

@Composable
fun StoreScreen() {
    var user by remember { mutableStateOf(Session.getCurrentUser()) }
    var puntosStr by remember { mutableStateOf("") }
    var emailPayPal by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var esExito by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            Session.getCurrentUser()?.id?.let { id ->
                RetrofitClient.apiService.getUserById(id).body()?.let {
                    Session.setCurrentUser(it)
                    user = it
                }
            }
        } catch (_: Exception) { }
    }

    val u = user ?: return

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Tienda - Canjear puntos", style = MaterialTheme.typography.headlineMedium)
        Text("Conectado con PayPal (simulación)", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))

        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Puntos disponibles: ${u.points}", style = MaterialTheme.typography.titleMedium)
                Text("Tasa: 1000 puntos = 1€", style = MaterialTheme.typography.bodySmall)
                Text("Mínimo para canjear: 1000 puntos", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = puntosStr,
            onValueChange = { puntosStr = it },
            label = { Text("Puntos a canjear") },
            placeholder = { Text("Ej: 5000") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = emailPayPal,
            onValueChange = { emailPayPal = it },
            label = { Text("Email de PayPal") },
            placeholder = { Text("tu@email.com") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(
                mensaje,
                color = if (esExito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        try {
                            RetrofitClient.apiService.getUserById(u.id!!).body()?.let {
                                Session.setCurrentUser(it)
                                user = it
                            }
                        } catch (_: Exception) { }
                    }
                }
            ) {
                Text("Actualizar puntos")
            }
            Button(
                onClick = {
                    val puntos = puntosStr.toIntOrNull() ?: 0
                    if (u.id == null) {
                        mensaje = "Error de sesión"
                        esExito = false
                        return@Button
                    }
                    if (puntosStr.isBlank()) {
                        mensaje = "Indica la cantidad de puntos"
                        esExito = false
                        return@Button
                    }
                    if (puntos < 1000) {
                        mensaje = "Mínimo 1000 puntos para canjear"
                        esExito = false
                        return@Button
                    }
                    if (emailPayPal.isBlank()) {
                        mensaje = "Indica tu email de PayPal"
                        esExito = false
                        return@Button
                    }
                    scope.launch {
                        try {
                            val resp = RetrofitClient.apiService.canjearPuntos(
                                CanjearPuntosRequest(u.id, puntos, emailPayPal.trim())
                            ).body()
                            if (resp != null && resp.exito) {
                                mensaje = "¡Canje exitoso! ${String.format("%.2f", resp.eurosTransferidos)}€ transferidos a PayPal."
                                esExito = true
                                puntosStr = ""
                                RetrofitClient.apiService.getUserById(u.id).body()?.let {
                                    Session.setCurrentUser(it)
                                    user = it
                                }
                            } else {
                                mensaje = resp?.mensaje ?: "Error al canjear"
                                esExito = false
                            }
                        } catch (e: Exception) {
                            mensaje = "Error: ${e.message}"
                            esExito = false
                        }
                    }
                }
            ) {
                Text("Canjear")
            }
        }
    }
}
