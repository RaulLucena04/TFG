package com.tfg.nbapredictor.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.FragmentMatchesBinding
import com.tfg.nbapredictor.network.RetrofitClient
import kotlinx.coroutines.launch

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMatches()
    }

    private fun loadMatches() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPartidos()
                if (response.isSuccessful) {
                    val partidos = response.body() ?: emptyList()
                    setupRecyclerView(partidos)
                } else {
                    Toast.makeText(context, "Error al cargar partidos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(partidos: List<com.tfg.nbapredictor.model.Partido>) {
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(context)
        // TODO: Crear adaptador específico para partidos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
