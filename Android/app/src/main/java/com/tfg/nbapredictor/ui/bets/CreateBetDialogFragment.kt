package com.tfg.nbapredictor.ui.bets

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.DialogCreateBetBinding
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

class CreateBetDialogFragment(
    private val onBetCreated: () -> Unit
) : DialogFragment() {

    private var _binding: DialogCreateBetBinding? = null
    private val binding get() = _binding!!
    private var partidosDisponibles: List<Partido> = emptyList()
    private var selectedPartido: Partido? = null
    private var selectedPrediccion: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCreateBetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Session.getCurrentUser() ?: return
        binding.tvPuntosDisponibles.text = "Puntos disponibles: ${user.points}"

        setupPrediccionSpinner()
        loadPartidos(user)
        setupListeners()
    }

    private fun setupPrediccionSpinner() {
        val opciones = arrayOf("LOCAL", "VISITANTE")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrediccion.adapter = adapter
        binding.spinnerPrediccion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                selectedPrediccion = opciones[pos]
                updateCuotaAndGanancia()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadPartidos(@Suppress("UNUSED_PARAMETER") user: User) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPartidos()
                if (response.isSuccessful) {
                    val todos = response.body() ?: emptyList()
                    partidosDisponibles = todos.filter { p ->
                        val e = p.estado?.uppercase() ?: ""
                        e == "PROGRAMADO" || e == "EN_CURSO"
                    }
                    val labels = if (partidosDisponibles.isEmpty()) {
                        listOf("No hay partidos disponibles")
                    } else {
                        partidosDisponibles.map { p ->
                            val local = p.equipoLocal?.nombre ?: "Local"
                            val visitante = p.equipoVisitante?.nombre ?: "Visitante"
                            "$local vs $visitante"
                        }
                    }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, labels)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerPartido.adapter = adapter
                    binding.spinnerPartido.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                            selectedPartido = partidosDisponibles.getOrNull(pos)
                            updateCuotaAndGanancia()
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                    if (partidosDisponibles.isEmpty()) {
                        showError("No hay partidos disponibles para apostar")
                    }
                }
            } catch (e: Exception) {
                showError("Error al cargar partidos: ${e.message}")
            }
        }
    }

    private fun setupListeners() {
        binding.etPuntos.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateCuotaAndGanancia()
            }
        })
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnCreate.setOnClickListener { createBet() }
    }

    private fun updateCuotaAndGanancia() {
        val partido = selectedPartido
        val prediccion = selectedPrediccion
        if (partido == null || prediccion == null) {
            binding.tvCuota.visibility = View.GONE
            binding.tvGananciaPotencial.visibility = View.GONE
            return
        }
        val cuota = calcularCuota(partido, prediccion)
        binding.tvCuota.text = "Cuota: %.2f".format(cuota)
        binding.tvCuota.visibility = View.VISIBLE
        val puntosStr = binding.etPuntos.text?.toString()?.trim()
        if (!puntosStr.isNullOrEmpty()) {
            val puntos = puntosStr.toIntOrNull() ?: 0
            if (puntos > 0) {
                binding.tvGananciaPotencial.text = "Ganancia potencial: %.0f puntos".format(puntos * cuota)
                binding.tvGananciaPotencial.visibility = View.VISIBLE
            } else {
                binding.tvGananciaPotencial.visibility = View.GONE
            }
        } else {
            binding.tvGananciaPotencial.visibility = View.GONE
        }
    }

    private fun calcularCuota(partido: Partido, prediccion: String): Double {
        val local = partido.equipoLocal?.nombre ?: "EquipoLocal"
        val visitante = partido.equipoVisitante?.nombre ?: "EquipoVisitante"
        val factorLocal = (local.hashCode() % 60 - 30) / 100.0
        val factorVisitante = (visitante.hashCode() % 60 - 30) / 100.0
        val variacion = if (prediccion.equals("LOCAL", ignoreCase = true)) -factorLocal else -factorVisitante
        var cuota = 1.9 + variacion
        cuota = maxOf(1.5, minOf(5.0, cuota))
        return (cuota * 100).toInt() / 100.0
    }

    private fun createBet() {
        hideError()
        val user = Session.getCurrentUser()
        if (user == null || user.id == null) {
            showError("No hay usuario en sesión")
            return
        }
        val partido = selectedPartido
        if (partido == null || partido.id == null) {
            showError("Selecciona un partido")
            return
        }
        val prediccion = selectedPrediccion
        if (prediccion.isNullOrEmpty()) {
            showError("Selecciona una predicción (LOCAL o VISITANTE)")
            return
        }
        val puntosStr = binding.etPuntos.text?.toString()?.trim()
        if (puntosStr.isNullOrEmpty()) {
            showError("Ingresa la cantidad de puntos")
            return
        }
        val puntos = puntosStr.toIntOrNull() ?: 0
        if (puntos <= 0) {
            showError("Los puntos deben ser mayores a 0")
            return
        }
        if (user.points < puntos) {
            showError("No tienes suficientes puntos. Disponibles: ${user.points}")
            return
        }
        val cuota = calcularCuota(partido, prediccion)
        val apuesta = Apuesta(
            puntosApostados = puntos,
            prediccion = prediccion,
            cuota = cuota,
            partido = Partido(id = partido.id),
            usuario = user
        )
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.createApuesta(apuesta)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Apuesta creada correctamente", Toast.LENGTH_SHORT).show()
                    onBetCreated()
                    dismiss()
                } else {
                    val body = response.errorBody()?.string()
                    showError(body ?: "Error al crear apuesta")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }
        }
    }

    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
