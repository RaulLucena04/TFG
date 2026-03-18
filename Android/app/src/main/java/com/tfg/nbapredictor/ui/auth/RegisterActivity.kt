package com.tfg.nbapredictor.ui.auth

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tfg.nbapredictor.ui.compose.screens.RegisterScreen

class RegisterActivity : AppCompatActivity() {
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            RegisterScreen(
                onRegisterSuccess = {
                    finish()
                },
                onLoginClick = {
                    finish()
                }
            )
        }
    }
}
