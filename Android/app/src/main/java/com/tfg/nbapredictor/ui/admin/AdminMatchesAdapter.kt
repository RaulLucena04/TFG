package com.tfg.nbapredictor.ui.admin

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.model.Partido
import java.time.format.DateTimeFormatter

class AdminMatchesAdapter(
    private val partidos: List<Partido>,
    private val onFinalizeClick: (Partido) -> Unit
) : RecyclerView.Adapter<AdminMatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_match, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(partidos[position])
    }

    override fun getItemCount() = partidos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMatchInfo: TextView = itemView.findViewById(R.id.tvMatchInfo)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        private val btnFinalizar: android.widget.Button = itemView.findViewById(R.id.btnFinalizar)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(partido: Partido) {
            val local = partido.equipoLocal?.nombre ?: "Local"
            val visitante = partido.equipoVisitante?.nombre ?: "Visitante"
            tvMatchInfo.text = "$local vs $visitante"
            partido.fecha?.let {
                val fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                tvEstado.text = "${it.format(fmt)} · ${partido.estado ?: "Programado"}"
            } ?: run { tvEstado.text = partido.estado ?: "Programado" }

            btnFinalizar.setOnClickListener { onFinalizeClick(partido) }
        }
    }
}
