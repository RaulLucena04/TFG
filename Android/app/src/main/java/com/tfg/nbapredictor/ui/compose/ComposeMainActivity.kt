package com.tfg.nbapredictor.ui.compose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.tfg.nbapredictor.ui.admin.AdminActivity
import com.tfg.nbapredictor.ui.auth.LoginActivity
import com.tfg.nbapredictor.util.Session

@RequiresApi(Build.VERSION_CODES.O)
class ComposeMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (!Session.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContent {
            NBAComposeApp(
                onLogout = {
                    Session.clearSession()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                },
                onAdminClick = {
                    startActivity(Intent(this, AdminActivity::class.java))
                },
                isAdmin = Session.isAdmin()
            )
        }
    }
}
