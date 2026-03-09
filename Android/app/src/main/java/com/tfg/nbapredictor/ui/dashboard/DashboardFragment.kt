package com.tfg.nbapredictor.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.FragmentDashboardBinding
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        lifecycleScope.launch {
            Session.userUpdated.collectLatest { loadData() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        loadData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadData() {
        val user = Session.getCurrentUser() ?: return

        lifecycleScope.launch {
            try {
                RetrofitClient.apiService.getUserById(user.id!!).body()?.let {
                    Session.setCurrentUser(it)
                }
            } catch (_: Exception) { }
            binding.tvTotalPoints.text = Session.getCurrentUser()?.points?.toString() ?: user.points.toString()

            try {
                // Cargar apuestas del usuario
                val uid = Session.getCurrentUser()?.id ?: user.id!!
                val apuestasResponse = RetrofitClient.apiService.getApuestasByUsuario(uid)
                if (apuestasResponse.isSuccessful) {
                    val apuestas = apuestasResponse.body() ?: emptyList()
                    val activas = apuestas.count { it.isActiva() }
                    val ganadas = apuestas.count { it.isGanada() }
                    val total = apuestas.size
                    
                    binding.tvActiveBets.text = activas.toString()
                    binding.tvWinRate.text = if (total > 0) {
                        "${(ganadas * 100 / total)}%"
                    } else {
                        "0%"
                    }
                }

                // Cargar partidos próximos
                val partidosResponse = RetrofitClient.apiService.getPartidos()
                if (partidosResponse.isSuccessful) {
                    val partidos = partidosResponse.body()?.filter { it.isProgramado() }?.take(5) ?: emptyList()
                    setupRecyclerView(partidos)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupRecyclerView(partidos: List<Partido>) {
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMatches.adapter = MatchesAdapter(partidos) { partido ->
            (activity as? com.tfg.nbapredictor.ui.main.MainActivity)?.loadMatchDetail(partido)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
