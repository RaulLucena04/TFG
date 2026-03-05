package com.tfg.nbapredictor.ui.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfg.nbapredictor.databinding.FragmentRankingsBinding
import com.tfg.nbapredictor.model.Apuesta
import com.tfg.nbapredictor.model.User
import com.tfg.nbapredictor.network.RetrofitClient
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.launch

class RankingsFragment : Fragment() {

    private var _binding: FragmentRankingsBinding? = null
    private val binding get() = _binding!!
    private var rankingRows: List<RankingRow> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRankingTypeSpinner()
        loadRankings()
    }

    private fun setupRankingTypeSpinner() {
        val opciones = arrayOf("Puntos Totales", "Tasa de Aciertos", "Ganancias Totales", "Apuestas Ganadas")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRankingType.adapter = adapter
        binding.spinnerRankingType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (rankingRows.isNotEmpty()) {
                    ordenarYMostrar(rankingRows, binding.spinnerRankingType.selectedItem.toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadRankings() {
        lifecycleScope.launch {
            try {
                val usersResponse = RetrofitClient.apiService.getAllUsers()
                if (!usersResponse.isSuccessful) {
                    Toast.makeText(context, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                val usuarios = usersResponse.body() ?: emptyList()

                val rows = mutableListOf<RankingRow>()
                for (usuario in usuarios) {
                    val apuestasResponse = RetrofitClient.apiService.getApuestasByUsuario(usuario.id!!)
                    val apuestas = if (apuestasResponse.isSuccessful) apuestasResponse.body() ?: emptyList() else emptyList()
                    rows.add(calcularEstadisticasUsuario(usuario, apuestas))
                }

                rankingRows = rows
                ordenarYMostrar(rows, binding.spinnerRankingType.selectedItem?.toString() ?: "Puntos Totales")
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calcularEstadisticasUsuario(usuario: User, apuestas: List<Apuesta>): RankingRow {
        val totalApuestas = apuestas.size
        val apuestasGanadas = apuestas.count { it.isGanada() }
        val apuestasFinalizadas = apuestas.count { it.isGanada() || it.isPerdida() }
        val tasaAciertos = if (apuestasFinalizadas > 0) {
            String.format("%.1f%%", apuestasGanadas * 100.0 / apuestasFinalizadas)
        } else "0.0%"
        val ganancias = apuestas.sumOf { apuesta ->
            when {
                apuesta.isGanada() -> {
                    val cuota = apuesta.cuota ?: 2.0
                    (apuesta.puntosApostados * cuota).toInt()
                }
                apuesta.isPerdida() -> -apuesta.puntosApostados
                else -> 0
            }
        }
        return RankingRow(
            posicion = 0,
            usuario = usuario.username,
            puntos = usuario.points,
            tasaAciertos = tasaAciertos,
            totalApuestas = totalApuestas,
            apuestasGanadas = apuestasGanadas,
            ganancias = ganancias,
            userId = usuario.id
        )
    }

    private fun ordenarYMostrar(rows: List<RankingRow>, tipoRanking: String) {
        val ordenados = when (tipoRanking) {
            "Tasa de Aciertos" -> rows.sortedWith(
                compareByDescending<RankingRow> { parseTasaAciertos(it.tasaAciertos) }.thenByDescending { it.puntos }
            )
            "Ganancias Totales" -> rows.sortedWith(
                compareByDescending<RankingRow> { it.ganancias }.thenByDescending { it.puntos }
            )
            "Apuestas Ganadas" -> rows.sortedWith(
                compareByDescending<RankingRow> { it.apuestasGanadas }.thenByDescending { it.puntos }
            )
            else -> rows.sortedByDescending { it.puntos }
        }

        val conPosicion = ordenados.mapIndexed { index, row -> row.copy(posicion = index + 1) }

        mostrarTop3(conPosicion)
        mostrarPosicionUsuario(conPosicion)
        binding.recyclerViewRankings.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRankings.adapter = RankingsAdapter(conPosicion)
    }

    private fun parseTasaAciertos(tasa: String): Double {
        return try {
            tasa.replace("%", "").toDouble()
        } catch (e: Exception) {
            0.0
        }
    }

    private fun mostrarTop3(rows: List<RankingRow>) {
        when {
            rows.size >= 3 -> {
                binding.tvSecondPlace.text = rows[1].usuario
                binding.tvSecondPoints.text = "${rows[1].puntos} pts"
                binding.tvFirstPlace.text = rows[0].usuario
                binding.tvFirstPoints.text = "${rows[0].puntos} pts"
                binding.tvThirdPlace.text = rows[2].usuario
                binding.tvThirdPoints.text = "${rows[2].puntos} pts"
            }
            rows.size == 2 -> {
                binding.tvFirstPlace.text = rows[0].usuario
                binding.tvFirstPoints.text = "${rows[0].puntos} pts"
                binding.tvSecondPlace.text = rows[1].usuario
                binding.tvSecondPoints.text = "${rows[1].puntos} pts"
                binding.tvThirdPlace.text = "-"
                binding.tvThirdPoints.text = "-"
            }
            rows.size == 1 -> {
                binding.tvFirstPlace.text = rows[0].usuario
                binding.tvFirstPoints.text = "${rows[0].puntos} pts"
                binding.tvSecondPlace.text = "-"
                binding.tvSecondPoints.text = "-"
                binding.tvThirdPlace.text = "-"
                binding.tvThirdPoints.text = "-"
            }
            else -> {
                binding.tvFirstPlace.text = "-"
                binding.tvFirstPoints.text = "-"
                binding.tvSecondPlace.text = "-"
                binding.tvSecondPoints.text = "-"
                binding.tvThirdPlace.text = "-"
                binding.tvThirdPoints.text = "-"
            }
        }
    }

    private fun mostrarPosicionUsuario(rows: List<RankingRow>) {
        val user = Session.getCurrentUser() ?: run {
            binding.tvUserPosition.text = "-#"
            binding.tvUserPoints.text = "0"
            return
        }
        val row = rows.find { it.userId == user.id }
        if (row != null) {
            binding.tvUserPosition.text = "#${row.posicion}"
            binding.tvUserPoints.text = row.puntos.toString()
        } else {
            binding.tvUserPosition.text = "-#"
            binding.tvUserPoints.text = user.points.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
