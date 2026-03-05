package com.tfg.nbapredictor.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.tfg.nbapredictor.databinding.DialogFinalizeMatchBinding
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.network.RetrofitClient
import kotlinx.coroutines.launch

class FinalizeMatchDialogFragment(
    private val partido: Partido,
    private val onFinalized: () -> Unit
) : DialogFragment() {

    private var _binding: DialogFinalizeMatchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFinalizeMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val local = partido.equipoLocal?.nombre ?: "Local"
        val visitante = partido.equipoVisitante?.nombre ?: "Visitante"
        binding.tvMatchTitle.text = "$local vs $visitante"
        binding.tilPuntosLocal.hint = "Puntos $local"
        binding.tilPuntosVisitante.hint = "Puntos $visitante"

        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnFinalizar.setOnClickListener { finalizar() }
    }

    private fun finalizar() {
        binding.tvError.visibility = View.GONE
        val puntosLocal = binding.etPuntosLocal.text?.toString()?.toIntOrNull()
        val puntosVisitante = binding.etPuntosVisitante.text?.toString()?.toIntOrNull()

        if (puntosLocal == null || puntosLocal < 0) {
            binding.tvError.text = "Ingresa puntos válidos para el equipo local"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        if (puntosVisitante == null || puntosVisitante < 0) {
            binding.tvError.text = "Ingresa puntos válidos para el equipo visitante"
            binding.tvError.visibility = View.VISIBLE
            return
        }

        val id = partido.id ?: return
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.finalizarPartido(id, puntosLocal, puntosVisitante)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Partido finalizado correctamente", Toast.LENGTH_SHORT).show()
                    onFinalized()
                    dismiss()
                } else {
                    binding.tvError.text = "Error al finalizar: ${response.code()}"
                    binding.tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                binding.tvError.text = "Error: ${e.message}"
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
