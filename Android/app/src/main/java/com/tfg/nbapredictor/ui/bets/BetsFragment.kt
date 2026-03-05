package com.tfg.nbapredictor.ui.bets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.databinding.FragmentBetsBinding
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

class BetsFragment : Fragment() {

    private var _binding: FragmentBetsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBets()
        binding.fabCreateBet.setOnClickListener {
            CreateBetDialogFragment {
                loadBets()
            }.show(parentFragmentManager, "CreateBet")
        }
    }

    override fun onResume() {
        super.onResume()
        loadBets()
    }

    private fun loadBets() {
        val user = Session.getCurrentUser()
        if (user == null || user.id == null) {
            Toast.makeText(context, "Inicia sesión para ver tus apuestas", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getApuestasByUsuario(user.id)
                if (response.isSuccessful) {
                    val apuestas = response.body() ?: emptyList()
                    actualizarUI(apuestas)
                } else {
                    Toast.makeText(context, "Error al cargar apuestas", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarUI(apuestas: List<Apuesta>) {
        val activas = apuestas.filter { it.isActiva() }
        val historial = apuestas.filter { it.isGanada() || it.isPerdida() }

        binding.tvTotalBets.text = apuestas.size.toString()
        val ganadas = apuestas.count { it.isGanada() }
        binding.tvTotalWins.text = ganadas.toString()
        val finalizadas = apuestas.count { it.isGanada() || it.isPerdida() }
        binding.tvWinRate.text = if (finalizadas > 0) {
            "${(ganadas * 100 / finalizadas)}%"
        } else "0%"

        binding.recyclerViewActiveBets.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewActiveBets.adapter = BetsAdapter(activas, showResultado = false)

        binding.recyclerViewBetHistory.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewBetHistory.adapter = BetsAdapter(historial, showResultado = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
