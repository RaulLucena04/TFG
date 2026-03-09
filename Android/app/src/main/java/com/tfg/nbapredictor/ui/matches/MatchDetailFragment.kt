package com.tfg.nbapredictor.ui.matches

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.databinding.FragmentMatchDetailBinding
import com.tfg.nbapredictor.model.Partido
import com.tfg.nbapredictor.ui.bets.CreateBetDialogFragment
import java.time.format.DateTimeFormatter

class MatchDetailFragment : Fragment() {

    companion object {
        private const val ARG_PARTIDO_ID = "partido_id"
        private const val ARG_PARTIDO_JSON = "partido_json"

        fun newInstance(partido: Partido): MatchDetailFragment {
            return MatchDetailFragment().apply {
                arguments = Bundle().apply {
                    partido.id?.let { putLong(ARG_PARTIDO_ID, it) }
                    putString(ARG_PARTIDO_JSON, com.google.gson.Gson().toJson(partido))
                }
            }
        }
    }

    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!
    private var partido: Partido? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        partido = arguments?.getString(ARG_PARTIDO_JSON)?.let {
            com.google.gson.Gson().fromJson(it, Partido::class.java)
        }
        partido?.let { bindMatch(it) } ?: run {
            Toast.makeText(context, "Partido no disponible", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }
        binding.btnBack.setOnClickListener { activity?.onBackPressed() }
        binding.btnCreateBet.setOnClickListener { openCreateBet() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindMatch(p: Partido) {
        val local = p.equipoLocal?.nombre ?: "Local"
        val visitante = p.equipoVisitante?.nombre ?: "Visitante"
        binding.tvMatchTitle.text = "$local vs $visitante"
        binding.tvMatchDate.text = p.fecha?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) ?: "Fecha no disponible"
        binding.tvMatchEstado.text = "Estado: ${p.estado ?: "PROGRAMADO"}"
        if (p.isFinalizado()) {
            binding.tvMarcador.visibility = View.VISIBLE
            binding.tvMarcador.text = "${p.puntosLocal ?: 0} - ${p.puntosVisitante ?: 0}"
            binding.btnCreateBet.visibility = View.GONE
        } else {
            binding.tvMarcador.visibility = View.GONE
            binding.btnCreateBet.visibility = View.VISIBLE
        }
    }

    private fun openCreateBet() {
        val p = partido ?: return
        CreateBetDialogFragment(
            preselectedPartido = p,
            onBetCreated = {
                Toast.makeText(context, "Apuesta creada", Toast.LENGTH_SHORT).show()
                refreshBetsInParent()
            }
        ).show(parentFragmentManager, "CreateBet")
    }

    private fun refreshBetsInParent() {
        (activity as? com.tfg.nbapredictor.ui.main.MainActivity)?.refreshBetsIfNeeded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
