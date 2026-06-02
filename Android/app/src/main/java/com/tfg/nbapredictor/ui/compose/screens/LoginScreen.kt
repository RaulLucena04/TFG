package com.tfg.nbapredictor.ui.compose.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.tfg.nbapredictor.network.SocketApi
import com.tfg.nbapredictor.util.ServerConfig
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showServerConfig by remember { mutableStateOf(false) }
    var serverAddress by remember {
        mutableStateOf("${ServerConfig.getServerHost(context)}:${ServerConfig.getServerPort(context)}")
    }
    
    // Mostrar diálogo de configuración de servidor la primera vez
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
        val hasConfigured = prefs.getBoolean("server_configured", false)
        if (!hasConfigured) {
            showServerConfig = true
        }
    }
    
    if (showServerConfig) {
        ServerConfigDialog(
            currentUrl = serverAddress,
            isEmulator = ServerConfig.isProbablyEmulator(),
            onDismiss = { showServerConfig = false },
            onUseDefault = {
                ServerConfig.applyDefaultSocketServer(context)
                serverAddress =
                    "${ServerConfig.getServerHost(context)}:${ServerConfig.getServerPort(context)}"
                val prefs = context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
                prefs.edit().putBoolean("server_configured", true).apply()
                showServerConfig = false
            },
            onSave = { url ->
                if (url.isBlank()) {
                    ServerConfig.applyDefaultSocketServer(context)
                } else {
                    ServerConfig.applyFromHostPortString(context, url.trim())
                }
                serverAddress =
                    "${ServerConfig.getServerHost(context)}:${ServerConfig.getServerPort(context)}"
                val prefs = context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
                prefs.edit().putBoolean("server_configured", true).apply()
                showServerConfig = false
            }
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "NBA Predictor",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        if (errorMessage != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        OutlinedTextField(
            value = username,
            onValueChange = { username = it; errorMessage = null },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = null },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = !isLoading
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    errorMessage = "Todos los campos son obligatorios"
                    return@Button
                }
                
                isLoading = true
                errorMessage = null
                
                scope.launch {
                    try {
                        val user = SocketApi.login(username.trim(), password)
                        Session.setCurrentUser(user)
                            onLoginSuccess()
                    } catch (e: Exception) {
                        val host = ServerConfig.getServerHost(context)
                        val port = ServerConfig.getServerPort(context)
                        val hint = if (ServerConfig.isProbablyEmulator()) {
                            "Comprueba que el backend esté en marcha y escuche el puerto $port. Si MySQL está parado, Spring puede no arrancar."
                        } else {
                            "En móvil físico no uses 10.0.2.2; pon la IP LAN de tu PC (ej. 192.168.1.138:$port), mismo Wi‑Fi, y firewall abierto para TCP $port."
                        }
                        errorMessage = "No se pudo conectar al servidor en $host:$port (${e.message}). $hint"
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Iniciar Sesión")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = { showServerConfig = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Configurar Servidor", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun ServerConfigDialog(
    currentUrl: String,
    isEmulator: Boolean,
    onDismiss: () -> Unit,
    onUseDefault: () -> Unit,
    onSave: (String) -> Unit
) {
    var url by remember(currentUrl) { mutableStateOf(currentUrl) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Configurar Servidor") },
        text = {
            Column {
                Text(
                    "La app no conecta a MySQL directamente: habla con el backend Java por TCP (socket) en el puerto 9090. " +
                        "El backend es quien usa la base de datos."
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (isEmulator) {
                        "Emulador: suele funcionar 10.0.2.2:9090 (apunta al localhost de tu PC)."
                    } else {
                        "Móvil físico: usa la IP de tu PC en la red Wi‑Fi (cmd → ipconfig), ej. 192.168.1.138:9090."
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Introduce host:puerto (ej: 10.0.2.2:9090):")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Servidor") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSave(url.trim()) }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onUseDefault) {
                Text("Usar por defecto")
            }
        }
    )
}
