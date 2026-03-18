package com.tfg.nbapredictor.ui.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tfg.nbapredictor.ui.compose.ComposeMainActivity
import com.tfg.nbapredictor.ui.compose.screens.LoginScreen
import com.tfg.nbapredictor.ui.compose.screens.RegisterScreen

class LoginActivity : AppCompatActivity() {
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            var showRegister by remember { mutableStateOf(false) }
            
            if (showRegister) {
                RegisterScreen(
                    onRegisterSuccess = {
                        finish()
                    },
                    onLoginClick = {
                        showRegister = false
                    }
                )
            } else {
                LoginScreen(
                    onLoginSuccess = {
                        val intent = Intent(this@LoginActivity, ComposeMainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    },
                    onRegisterClick = {
                        showRegister = true
                    }
                )
            }
        }
    }
}
