package com.tfg.nbapredictor.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.databinding.FragmentProfileBinding
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.ui.bets.BetsAdapter
import com.tfg.nbapredictor.ui.auth.LoginActivity
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        loadStatsAndRecentBets()
        lifecycleScope.launch {
            Session.userUpdated.collectLatest {
                refreshUserFromApi()
            }
        }
        binding.btnLogout.setOnClickListener {
            Session.clearSession()
            startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshUserFromApi()
        loadUserData()
        loadStatsAndRecentBets()
    }

    private fun loadUserData() {
        val user = Session.getCurrentUser()
        user?.let {
            binding.tvUsername.text = it.username
            binding.tvEmail.text = it.email ?: "No disponible"
            binding.tvPoints.text = it.points.toString()
        } ?: run {
            binding.tvUsername.text = "-"
            binding.tvEmail.text = "-"
            binding.tvPoints.text = "0"
        }
    }

    private fun refreshUserFromApi() {
        val user = Session.getCurrentUser() ?: return
        val userId = user.id ?: return
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getUserById(userId)
                if (response.isSuccessful) {
                    response.body()?.let { updated ->
                        Session.setCurrentUser(updated)
                        Session.notifyUserUpdated()
                        loadUserData()
                        loadStatsAndRecentBets()
                    }
                }
            } catch (_: Exception) { }
        }
    }

    private fun loadStatsAndRecentBets() {
        val user = Session.getCurrentUser() ?: return
        binding.tvPoints.text = user.points.toString()
        val userId = user.id ?: return

        lifecycleScope.launch {
            try {
                val apuestasResponse = RetrofitClient.apiService.getApuestasByUsuario(userId)
                if (!apuestasResponse.isSuccessful) return@launch
                val apuestas = apuestasResponse.body() ?: emptyList()

                binding.tvPoints.text = Session.getCurrentUser()?.points?.toString() ?: user.points.toString()
                binding.tvTotalBets.text = apuestas.size.toString()
                val finalizadas = apuestas.count { it.isGanada() || it.isPerdida() }
                val ganadas = apuestas.count { it.isGanada() }
                binding.tvWinRate.text = if (finalizadas > 0) {
                    String.format("%.1f%%", ganadas * 100.0 / finalizadas)
                } else "0%"

                val usersResponse = RetrofitClient.apiService.getAllUsers()
                if (usersResponse.isSuccessful) {
                    val usuarios = usersResponse.body()?.sortedByDescending { it.points } ?: emptyList()
                    val posicion = usuarios.indexOfFirst { it.id == user.id }
                    binding.tvRanking.text = if (posicion >= 0) "#${posicion + 1}" else "#?"
                } else {
                    binding.tvRanking.text = "#?"
                }

                cargarApuestasRecientes(apuestas.take(10))
            } catch (_: Exception) { }
        }
    }

    private fun cargarApuestasRecientes(apuestas: List<Apuesta>) {
        binding.recyclerViewRecentBets.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRecentBets.adapter = BetsAdapter(apuestas, showResultado = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
