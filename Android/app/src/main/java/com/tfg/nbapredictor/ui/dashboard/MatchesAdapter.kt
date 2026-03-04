package com.tfg.nbapredictor.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.model.Partido
import java.time.format.DateTimeFormatter

class MatchesAdapter(private val partidos: List<Partido>) :
    RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(partidos[position])
    }

    override fun getItemCount() = partidos.size

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTeams: TextView = itemView.findViewById(R.id.tvTeams)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(partido: Partido) {
            val local = partido.equipoLocal?.nombre ?: "Equipo Local"
            val visitante = partido.equipoVisitante?.nombre ?: "Equipo Visitante"
            tvTeams.text = "$local vs $visitante"
            
            partido.fecha?.let {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                tvDate.text = it.format(formatter)
            } ?: run {
                tvDate.text = "Fecha no disponible"
            }
        }
    }
}
