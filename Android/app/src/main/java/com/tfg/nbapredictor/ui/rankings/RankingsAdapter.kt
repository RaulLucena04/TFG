package com.tfg.nbapredictor.ui.rankings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tfg.nbapredictor.R

data class RankingRow(
    val posicion: Int,
    val usuario: String,
    val puntos: Int,
    val tasaAciertos: String,
    val totalApuestas: Int,
    val apuestasGanadas: Int,
    val ganancias: Int,
    val userId: Long?
)

class RankingsAdapter(private val items: List<RankingRow>) :
    RecyclerView.Adapter<RankingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPosicion: TextView = itemView.findViewById(R.id.tvPosicion)
        private val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuario)
        private val tvPuntos: TextView = itemView.findViewById(R.id.tvPuntos)

        fun bind(row: RankingRow) {
            tvPosicion.text = row.posicion.toString()
            tvUsuario.text = row.usuario
            tvPuntos.text = "${row.puntos} pts"
        }
    }
}
