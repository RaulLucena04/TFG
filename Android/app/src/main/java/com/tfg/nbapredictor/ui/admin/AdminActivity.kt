package com.tfg.nbapredictor.ui.admin

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tfg.nbapredictor.ui.compose.screens.AdminScreen
import com.tfg.nbapredictor.util.Session

class AdminActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Session.isAdmin()) {
            finish()
            return
        }

        supportActionBar?.title = "Panel de Administración"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContent {
            AdminScreen()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

