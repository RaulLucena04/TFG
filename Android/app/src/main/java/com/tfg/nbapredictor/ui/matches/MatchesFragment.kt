package com.tfg.nbapredictor.ui.matches

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.databinding.FragmentMatchesBinding
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.ui.dashboard.MatchesAdapter
import kotlinx.coroutines.launch

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!
    private var todosLosPartidos: List<Partido> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilterSpinner()
        loadMatches()
    }

    private fun setupFilterSpinner() {
        val opciones = arrayOf("Todos", "Próximos", "En curso", "Finalizados")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilter.adapter = adapter
        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                aplicarFiltro()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadMatches() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPartidos()
                if (response.isSuccessful) {
                    todosLosPartidos = response.body() ?: emptyList()
                    aplicarFiltro()
                } else {
                    Toast.makeText(context, "Error al cargar partidos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun aplicarFiltro() {
        val filtro = binding.spinnerFilter.selectedItem?.toString() ?: "Todos"
        val filtrados = when (filtro) {
            "Próximos" -> todosLosPartidos.filter { it.isProgramado() }
            "En curso" -> todosLosPartidos.filter { "EN_CURSO".equals(it.estado, ignoreCase = true) }
            "Finalizados" -> todosLosPartidos.filter { it.isFinalizado() }
            else -> todosLosPartidos
        }
        setupRecyclerView(filtrados)
    }

    private fun setupRecyclerView(partidos: List<Partido>) {
        binding.recyclerViewMatches.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewMatches.adapter = MatchesAdapter(partidos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
