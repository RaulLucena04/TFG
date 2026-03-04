package com.tfg.nbapredictor.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.ActivityAdminBinding
import com.tfg.nbapredictor.util.Session

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Session.isAdmin()) {
            finish()
            return
        }

        supportActionBar?.title = getString(R.string.admin_dashboard)
    }
}
