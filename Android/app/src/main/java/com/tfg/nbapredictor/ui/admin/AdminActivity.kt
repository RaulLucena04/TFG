package com.tfg.nbapredictor.ui.admin

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.ActivityAdminBinding
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Session.isAdmin()) {
            finish()
            return
        }

        supportActionBar?.title = getString(R.string.admin_dashboard)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnRefresh.setOnClickListener { loadData() }
        loadData()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData() {
        lifecycleScope.launch {
            try {
                val usersRes = RetrofitClient.apiService.getAllUsers()
                val partidosRes = RetrofitClient.apiService.getPartidos()
                val equiposRes = RetrofitClient.apiService.getEquipos()

                val users = usersRes.body() ?: emptyList()
                val partidos = partidosRes.body() ?: emptyList()
                val equipos = equiposRes.body() ?: emptyList()

                binding.tvTotalUsers.text = users.size.toString()
                val activos = partidos.count { p ->
                    val e = p.estado?.uppercase() ?: ""
                    e == "PROGRAMADO" || e == "EN_CURSO" || e == "EN CURSO"
                }
                binding.tvActiveMatches.text = activos.toString()
                binding.tvTotalBets.text = "N/A"
                binding.tvTotalTeams.text = equipos.size.toString()

                val pendientes = partidos.filter { p ->
                    val e = p.estado?.uppercase() ?: ""
                    e == "PROGRAMADO" || e.isEmpty()
                }.take(20)
                binding.recyclerViewPendingMatches.layoutManager = LinearLayoutManager(this@AdminActivity)
                binding.recyclerViewPendingMatches.adapter = AdminMatchesAdapter(pendientes) { partido ->
                    FinalizeMatchDialogFragment(partido) { loadData() }
                        .show(supportFragmentManager, "FinalizeMatch")
                }
            } catch (e: Exception) {
                Toast.makeText(this@AdminActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
